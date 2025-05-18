package com.github.piperweb.adapter.kubernetes.executor.job

import com.github.piper.kubernetes.KubernetesDefaults.NAMESPACE
import com.github.piperweb.adapter.kubernetes.informer.DagInformer
import com.github.piperweb.application.port.K8sJobExecutorPort
import io.fabric8.kubernetes.api.model.ConfigMap
import io.fabric8.kubernetes.api.model.batch.v1.Job
import io.fabric8.kubernetes.client.KubernetesClient
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class K8sJobExecutor(
    val client: KubernetesClient
): K8sJobExecutorPort {
    private val log = LoggerFactory.getLogger(DagInformer::class.java)

    override fun execute(executorRequest: ExecutorRequest) {

        val job = executorRequest.build()

        val configMap = createConfigMap(
            name = executorRequest.configMapVolumeSourceName,
            script = executorRequest.script,
            scriptPath = executorRequest.scriptPath,
        )

        // TODO: Better error handling here!
        log.info("Creating configmap ${executorRequest.configMapVolumeSourceName}")
        createConfigMap(configMap)

        log.info("Executing job ${executorRequest.name}")
        executeJob(job)

        log.info("Finished executing job ${executorRequest.name}")
        return
    }

    private fun executeJob(job: Job) {
        try {
            client.batch()
                .v1()
                .jobs()
                .inNamespace(NAMESPACE)
                .resource(job)
                .create()
        } catch (e: Exception) {
            throw ExecutorException("Failed to created job", e)
        }
    }

    private fun deleteJob(name: String) {
        try {
            client.batch()
                .v1()
                .jobs()
                .inNamespace(NAMESPACE)
                .withName(name)
                .delete()
        } catch (e: Exception) {
            throw ExecutorException("Failed to delete job", e)
        }
    }

    private fun createConfigMap(configMap: ConfigMap) {
        try {
            client
                .configMaps()
                .inNamespace(NAMESPACE)
                .resource(configMap)
                .create()
        } catch (e: Exception) {
            throw ExecutorException("Failed to created configMap", e)
        }
    }

    private fun deleteConfigmap(configMap: ConfigMap) {
        try {
            client
                .configMaps()
                .inNamespace(NAMESPACE)
                .resource(configMap)
                .delete()
            client.configMaps().delete()
        } catch (e: Exception) {
            throw ExecutorException("Failed to created configMap", e)
        }
    }
}