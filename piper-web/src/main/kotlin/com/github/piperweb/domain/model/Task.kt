package com.github.piperweb.domain.model

import com.github.piper.primitives.kubernetes.K8sResourceStatus
import com.github.piper.primitives.kubernetes.K8sTaskResources
import java.time.LocalDateTime
import java.util.*

data class Task(
    val id: UUID,
    val name: String,
    val createdAt: LocalDateTime,
    val status: K8sResourceStatus,
    val startTime: LocalDateTime?,
    val finishedAt: LocalDateTime?,
    val resources: K8sTaskResources,

    val dagId: UUID? = null,
    val dependsOn: Task? = null,
)


