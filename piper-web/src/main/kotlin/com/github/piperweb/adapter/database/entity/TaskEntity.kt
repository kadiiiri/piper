package com.github.piperweb.adapter.database.entity

import com.github.piper.primitives.kubernetes.K8sResourceStatus
import jakarta.persistence.CascadeType.ALL
import jakarta.persistence.Entity
import jakarta.persistence.FetchType.LAZY
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import java.time.LocalDateTime
import java.util.*


@Entity
@Table(name = "task")
data class TaskEntity(
    @Id
    val id: UUID? = UUID.randomUUID(),
    val name: String,
    val createdAt: LocalDateTime,

    val status: K8sResourceStatus,
    val startTime: LocalDateTime?,
    val endTime: LocalDateTime?,
    @OneToOne(cascade = [ALL], fetch = LAZY)
    val resources: K8sResourcesEntity,

    @JoinColumn(name = "dag_id")
    val dagId: UUID? = null,

    @OneToOne(cascade = [ALL], fetch = LAZY)
    @JoinColumn(name = "parent_id")
    val dependsOn: TaskEntity? = null,
)

@Entity
@Table(name = "resource")
data class K8sResourcesEntity(
    @Id
    val id: UUID? = UUID.randomUUID(),
    var minCpuCores: Double = 4.0,
    var minMemory: Double = 1000.0,
    var maxCpuCores: Double = 4.0,
    var maxMemory: Double = 1000.0,
)