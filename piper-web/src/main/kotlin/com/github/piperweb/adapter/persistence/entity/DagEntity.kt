package com.github.piperweb.adapter.persistence.entity

import com.github.piper.primitives.kubernetes.K8sResourceStatus
import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import java.time.LocalDateTime
import java.util.*


@Entity
@Table(name = "dag")
data class DagEntity(
    @Id
    val id: UUID? = UUID.randomUUID(),
    val name: String,
    val createdAt: LocalDateTime,
    val status: K8sResourceStatus,

    @OneToOne(cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    @JoinColumn(name = "schedule_id")
    val schedule: ScheduleEntity,
)

