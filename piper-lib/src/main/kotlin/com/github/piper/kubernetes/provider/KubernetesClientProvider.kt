package com.github.piper.kubernetes.provider

import com.github.piper.kubernetes.KubernetesDefaults.NAMESPACE
import io.fabric8.kubernetes.api.model.ConfigMap
import io.fabric8.kubernetes.api.model.batch.v1.Job
import io.fabric8.kubernetes.client.KubernetesClient
import io.fabric8.kubernetes.client.KubernetesClientBuilder

class KubernetesClientProvider {
    private val client: KubernetesClient = KubernetesClientBuilder().build()

    fun executeJob(job: Job) {
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

    fun deleteJob(name: String) {
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

    fun createConfigMap(configMap: ConfigMap) {
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

}

class ExecutorException(message: String, cause: Throwable) : RuntimeException(message, cause)