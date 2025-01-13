package adapter.kubernetes

import io.github.oshai.kotlinlogging.KLogger
import io.github.oshai.kotlinlogging.KotlinLogging.logger
import io.kubernetes.client.common.KubernetesObject
import io.kubernetes.client.openapi.ApiClient
import io.kubernetes.client.openapi.ApiException
import io.kubernetes.client.openapi.apis.BatchV1Api
import io.kubernetes.client.openapi.apis.CoreV1Api
import io.kubernetes.client.openapi.models.*
import io.kubernetes.client.util.Config.defaultClient
import java.nio.file.Path
import java.util.concurrent.TimeUnit
import kotlin.io.path.readText


abstract class KubeAdapter(
    protected val namespace: String = "default"
) {
    private val client: ApiClient = defaultClient()
    protected val batchApi: BatchV1Api = BatchV1Api(client)
    protected val coreApi: CoreV1Api = CoreV1Api(client)
    protected val log: KLogger = logger {}
    protected val configMapName: String = "script-config"
    protected val volumeName: String = "script-volume"

    abstract fun run(resourceName: String,
                     image: String,
                     command: List<String>,
                     args: List<String>,
                     script: Path
    ): KubernetesObject

    fun awaitCompletion(resourceName: String) {
        log.info { "Waiting for completion of job with name '$resourceName'" }

        while (true) {
            try {
                val jobResult = batchApi.readNamespacedJob(resourceName, namespace).execute()
                val jobStatus = jobResult.status

                if (jobStatus?.succeeded != null && jobStatus.succeeded!! > 0) {
                    log.info { "Job '$resourceName' succeeded" }
                    break
                } else if (jobStatus?.failed != null && jobStatus.failed!! > 0) {
                    log.error { "Job '$resourceName' failed" }
                    break
                }

                log.info { "Job '$resourceName' is still running..." }
                TimeUnit.SECONDS.sleep(3)

            } catch (e: ApiException) {
                log.error { "Exception when reading Job '$resourceName' status: ${e.responseBody}" }
                e.printStackTrace()
                break
            }
        }
    }

    protected fun createNamespacedConfigMap(resourceName: String, script: Path) {
        val configMap = V1ConfigMap()
            .metadata(V1ObjectMeta().name("$resourceName-$configMapName"))
            .putDataItem(script.getName(script.nameCount - 1).toString(), script.readText())

        try {
            coreApi.createNamespacedConfigMap(
                namespace,
                configMap
            ).execute()
        } catch (e: ApiException) {
            log.info { "ConfigMap already exists: ${e.responseBody}" }
        }
    }

    protected fun deleteNamespacedConfigMap(resourceName: String) {
        try {
            coreApi.deleteNamespacedConfigMap(
                "$resourceName-$configMapName",
                namespace
            ).execute()
        } catch (e: ApiException) {
            log.info { "Trying to delete ConfigMap, but did not exist ${e.responseBody}, proceeding to create it." }
        }
    }

    protected fun createV1EnvVars(envVars: Map<String, String>): List<V1EnvVar> {
        return envVars.entries.map { createV1EnvVar(it.key, it.value) }
    }

    protected fun createV1Volume(resourceName: String): V1Volume {
        return V1Volume().apply {
            name = volumeName
            configMap = createV1ConfigMapVolumeSource(resourceName)
        }
    }

    protected fun createV1VolumeMount(): V1VolumeMount {
        return V1VolumeMount().apply {
            name = volumeName
            mountPath = "/scripts"
        }
    }

    private fun createV1EnvVar(name: String, value: String): V1EnvVar {
        return V1EnvVar().apply {
            this.name = name
            this.value = value
        }
    }

    private fun createV1ConfigMapVolumeSource(podName: String): V1ConfigMapVolumeSource {
        return V1ConfigMapVolumeSource().apply {
            name = "$podName-$configMapName"
            defaultMode = 511
        }
    }
}
