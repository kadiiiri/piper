package com.eska.piperweb.core.service

import com.eska.piperweb.core.usecase.FindKubernetesJobUseCase
import com.eska.piperweb.port.KubernetesPort
import io.fabric8.kubernetes.api.model.batch.v1.Job
import org.springframework.stereotype.Service

@Service
class KubernetesJobService(
    private val kubernetesPort: KubernetesPort
): FindKubernetesJobUseCase {
    override fun findAll(): List<Job> = kubernetesPort.findAllJobs()
    override fun findAll(name: String): Job = kubernetesPort.findJob(name)
}