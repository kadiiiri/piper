package com.github.piperweb.adapter.database.entity

import com.github.piper.primitives.kubernetes.K8sResourceStatus
import jakarta.persistence.Entity
import jakarta.persistence.Id
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
    val scheduledFor: LocalDateTime,
)



