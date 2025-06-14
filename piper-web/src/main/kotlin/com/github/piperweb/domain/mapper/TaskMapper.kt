package com.github.piperweb.domain.mapper

import com.github.piper.primitives.kubernetes.K8sResourceStatus
import com.github.piperweb.adapter.kubernetes.executor.job.ExecutorRequest
import com.github.piperweb.adapter.persistence.entity.TaskEntity
import com.github.piperweb.domain.mapper.K8sResourcesMapper.toEntity
import com.github.piperweb.domain.mapper.K8sResourcesMapper.toModel
import com.github.piperweb.domain.model.Task

object TaskMapper {
    fun Task.toEntity(): TaskEntity = TaskEntity(
        id = id,
        name = name,
        createdAt = createdAt,
        status = K8sResourceStatus.fromString(status.name),
        startTime = startTime,
        endTime = finishedAt,
        script = script,
        scriptPath = scriptPath,
        command = command,
        image = image,
        resources = resources.toEntity(),
        dagId = dagRef,
        dependsOn = dependsOn?.toEntity(),
    )

    fun TaskEntity.toModel(): Task = Task(
        id = id!!,
        name = name,
        createdAt = createdAt,
        status = K8sResourceStatus.fromString(status.name),
        startTime = startTime,
        finishedAt = endTime,
        script = script,
        scriptPath = scriptPath,
        command = command,
        image = image,
        resources = resources.toModel(),
        dagRef = dagId!!,
        dependsOn = dependsOn?.toModel(),
    )
    
    fun Task.toExecutorRequest() = ExecutorRequest(
        name = "$name-$id",
        image = image,
        command = command,
        script = script,
        scriptPath = scriptPath,
        minCpuCores = resources.minCpuCores,
        minMemory = resources.minMemory,
        maxCpuCores = resources.maxCpuCores,
        maxMemory = resources.maxMemory,
    )
}