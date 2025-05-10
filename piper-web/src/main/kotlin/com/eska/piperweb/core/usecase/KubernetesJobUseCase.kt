package com.eska.piperweb.core.usecase

import io.fabric8.kubernetes.api.model.batch.v1.Job

interface FindKubernetesJobUseCase {
    fun findAll(name: String): Job
    fun findAll(): List<Job>
}