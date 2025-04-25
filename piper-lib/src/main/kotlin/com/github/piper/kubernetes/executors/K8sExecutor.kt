package com.github.piper.kubernetes.executors

import com.github.piper.kubernetes.KubernetesDefaults.EXIT_CODE_FAILURE
import com.github.piper.kubernetes.KubernetesDefaults.EXIT_CODE_SUCCESS
import com.github.piper.kubernetes.provider.KubernetesClientProvider
import com.github.piper.kubernetes.resources.createConfigMap
import io.github.oshai.kotlinlogging.KotlinLogging

class K8sExecutor(val executorRequest: ExecutorRequest) {
    internal val log = KotlinLogging.logger {}
    private val kubernetesClientProvider = KubernetesClientProvider()
    internal val job = executorRequest.build()

    fun execute(): ExecutorResponse {

        val configMap = createConfigMap(
            executorRequest.configMapVolumeSourceName,
            executorRequest.script
        )

        try {
            log.info { "Creating configmap ${executorRequest.configMapVolumeSourceName}" }
            kubernetesClientProvider.createConfigMap(configMap)

            log.info { "Executing job ${executorRequest.name}" }
            kubernetesClientProvider.executeJob(job)
        } catch (e: Exception) {
            kubernetesClientProvider.deleteJob(executorRequest.name)
            log.error { "Failed to run executor for job $job with exception ${e.message}" }
            return ExecutorResponse(EXIT_CODE_FAILURE)
        }

        log.info { "Finished executing job ${executorRequest.name}" }
        return ExecutorResponse(EXIT_CODE_SUCCESS)
    }
}

