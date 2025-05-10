package com.eska.piperweb.core.usecase

import com.eska.piperweb.adapter.database.entities.Job
import com.eska.piperweb.adapter.kubernetes.view.JobView

interface FindAllJobsUseCase {
    fun findAll(): List<Job>
}

interface FindJobByNameUseCase {
    fun find(name: String): Job?
}

interface SaveJobUseCase {
    fun save(jobView: JobView)
}

interface UpdateJobUseCase {
    fun update(jobView: JobView)
}

interface DeleteJobUseCase {
    fun delete(jobView: JobView)
}
