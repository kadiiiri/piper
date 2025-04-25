package com.eska.piperweb.port

import io.fabric8.kubernetes.api.model.batch.v1.Job


interface KubernetesPort {
    fun findJob(name: String): Job
    fun findAllJobs(): List<Job>
}
