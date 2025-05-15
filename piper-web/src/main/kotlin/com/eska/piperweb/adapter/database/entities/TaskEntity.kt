package com.eska.piperweb.adapter.database.entities

import com.eska.piperweb.domain.model.ResourceStatus
import jakarta.persistence.CascadeType.ALL
import jakarta.persistence.Entity
import jakarta.persistence.FetchType.LAZY
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.time.LocalDateTime
import java.util.*


@Entity
@Table(name = "task")
data class TaskEntity(
    @Id
    val id: UUID,
    val name: String,
    val createdAt: LocalDateTime,

    val status: ResourceStatus,
    val startTime: LocalDateTime?,
    val endTime: LocalDateTime?,

    @ManyToOne(cascade = [ALL], fetch = LAZY)
    @JoinColumn(name = "dag_id")
    val dag: DagEntity? = null,
)