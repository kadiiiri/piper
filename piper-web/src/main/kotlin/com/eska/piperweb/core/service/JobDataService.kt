package com.eska.piperweb.core.service

import com.eska.piperweb.adapter.database.entities.Job
import com.eska.piperweb.adapter.database.entities.JobDataRepository
import com.eska.piperweb.adapter.kubernetes.JobView
import com.eska.piperweb.core.domain.usecases.DeleteJobUseCase
import com.eska.piperweb.core.domain.usecases.FindAllJobsUseCase
import com.eska.piperweb.core.domain.usecases.FindJobByNameUseCase
import com.eska.piperweb.core.domain.usecases.SaveJobUseCase
import com.eska.piperweb.core.domain.usecases.UpdateJobUseCase
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
            creationTimeStamp = jobView.creationTimeStamp,
            startTime = jobView.startTime,
            status = jobView.status
        ))
    }

    override fun update(jobView: JobView) {
        jobDataRepository.save(Job(
            id = jobView.id,
            name = jobView.name,
            namespace = jobView.namespace,
            creationTimeStamp = jobView.creationTimeStamp,
            startTime = jobView.startTime,
            status = jobView.status
        ))
    }

    override fun delete(jobView: JobView) {
        jobDataRepository.deleteById(jobView.id)
    }
}