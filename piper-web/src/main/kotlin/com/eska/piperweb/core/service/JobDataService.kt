package com.eska.piperweb.core.service

import com.eska.piperweb.adapter.database.entities.Job
import com.eska.piperweb.adapter.database.entities.JobDataRepository
import com.eska.piperweb.adapter.kubernetes.JobView
import com.eska.piperweb.core.usecase.DeleteJobUseCase
import com.eska.piperweb.core.usecase.FindAllJobsUseCase
import com.eska.piperweb.core.usecase.FindJobByNameUseCase
import com.eska.piperweb.core.usecase.SaveJobUseCase
import com.eska.piperweb.core.usecase.UpdateJobUseCase
import org.springframework.stereotype.Service

@Service
class JobDataService(
    val jobDataRepository: JobDataRepository
): FindJobByNameUseCase, FindAllJobsUseCase, SaveJobUseCase, UpdateJobUseCase, DeleteJobUseCase {

    override fun find(name: String): Job? = jobDataRepository.findByName(name)

    override fun findAll(): List<Job> = jobDataRepository.findAll()

    override fun save(jobView: JobView) {
        jobDataRepository.save(Job(
            id = jobView.id,
            name = jobView.name,
            namespace = jobView.namespace,
            createdAt = jobView.creationTimeStamp,
            startTime = jobView.startTime,
            status = jobView.status
        ))
    }

    override fun update(jobView: JobView) {
        jobDataRepository.save(Job(
            id = jobView.id,
            name = jobView.name,
            namespace = jobView.namespace,
            createdAt = jobView.creationTimeStamp,
            startTime = jobView.startTime,
            status = jobView.status
        ))
    }

    override fun delete(jobView: JobView) {
        jobDataRepository.deleteById(jobView.id)
    }
}