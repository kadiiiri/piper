package adapter.kubernetes

import adapter.kubernetes.job.Job
import adapter.kubernetes.job.JobMonitor
import adapter.kubernetes.job.build
import adapter.kubernetes.run.RunConfig
import io.kubernetes.client.common.KubernetesObject
import io.kubernetes.client.openapi.models.V1Job
import kotlinx.coroutines.runBlocking


class KubeJobAdapter(private val namespace: String = "default") : KubeAdapter(namespace) {
    private val jobMonitor = JobMonitor(clientProvider.batchApi, namespace)
    private val defaultEnv = mapOf("KOTLIN_HOME" to "/opt/kotlinc")

    override fun run(runConfig: RunConfig): V1Job {

        val configMapName = "${runConfig.resourceName}-script-config";

        deleteConfigMap(configMapName)
        createConfigMap(configMapName, runConfig.script)

        val jobDefinition = Job(
            resourceName = runConfig.resourceName,
            image = runConfig.image,
            command = runConfig.command,
            args = runConfig.args,
            env = defaultEnv.plus(runConfig.env)
        ).build()

        val v1Job = clientProvider.batchApi.createNamespacedJob(this.namespace, jobDefinition).execute()

        awaitCompletion(runConfig.resourceName)
        return v1Job
    }

    override fun runParallel(
        vararg runConfig: RunConfig,
    ): KubernetesObject {
        TODO("Not yet implemented")
    }

    fun awaitCompletion(resourceName: String) {
        runBlocking { jobMonitor.awaitCompletion(resourceName) }
    }
}

