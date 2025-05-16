package com.github.piperweb.domain.mapper

import com.github.piper.primitives.kubernetes.K8sResourceStatus
import com.github.piperweb.adapter.database.entity.TaskEntity
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
        resources = resources.toEntity(),
        dagId = dagId,
        dependsOn = dependsOn?.toEntity(),
    )

    fun TaskEntity.toModel(): Task = Task(
        id = id!!,
        name = name,
        createdAt = createdAt,
        status = K8sResourceStatus.fromString(status.name),
        startTime = startTime,
        finishedAt = endTime,
        dagId = dagId,
        resources = resources.toModel(),
        dependsOn = dependsOn?.toModel(),
    )
}