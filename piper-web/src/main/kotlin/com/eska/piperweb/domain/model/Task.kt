package com.eska.piperweb.domain.model

import com.eska.piperweb.adapter.database.entities.TaskEntity
import java.time.LocalDateTime
import java.util.*

data class Task(
    val id: UUID,
    val name: String,
    val createdAt: LocalDateTime,
    val status: ResourceStatus,
    val startTime: LocalDateTime?,
    val endTime: LocalDateTime?,

    val dagId: UUID?,
    val dependsOn: List<UUID> = emptyList(),
)

fun Task.toEntity() = TaskEntity(
    id = id,
    name = name,
    createdAt = createdAt,
    status = ResourceStatus.fromString(status.name),
    startTime = startTime,
    endTime = endTime,
    dag = null,
)
