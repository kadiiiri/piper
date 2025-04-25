package com.eska.piperweb.adapter.kubernetes

import com.eska.piperweb.port.KubernetesPort
import io.fabric8.kubernetes.api.model.batch.v1.Job
import io.fabric8.kubernetes.client.KubernetesClient
import org.springframework.stereotype.Service

@Service
class KubernetesAdapter(
    private val client: KubernetesClient
) : KubernetesPort {

    private val namespace = client.namespace ?: "default" // TODO: This doesn't belong here

    override fun findJob(name: String): Job {
        return client.batch().v1()
            .jobs()
            .inNamespace(namespace)
            .withName(name)
            .get()
            ?: throw IllegalStateException("Job with name: '$name' not found in namespace: '$namespace'")
    }

    override fun findAllJobs(): List<Job> {
        return client.batch().v1()
            .jobs()
            .inNamespace(namespace)
            .list()
            .items
    }
}
