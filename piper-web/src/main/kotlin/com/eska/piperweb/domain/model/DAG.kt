package com.eska.piperweb.domain.model

import com.eska.piperweb.adapter.database.entities.DagEntity
import java.time.LocalDateTime
import java.util.*

data class DAG(
    val id: UUID,
    val name: String,
    val createdAt: String,
    val status: ResourceStatus,
    val scheduledFor: LocalDateTime,
)

fun DAG.toEntity() = DagEntity(id, name, createdAt, status, scheduledFor)