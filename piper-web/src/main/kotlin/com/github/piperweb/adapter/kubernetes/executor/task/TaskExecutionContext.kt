package com.github.piperweb.adapter.kubernetes.executor.task

import com.github.piper.primitives.kubernetes.K8sResourceStatus
import com.github.piper.primitives.kubernetes.K8sTaskResources
import com.github.piperweb.domain.model.Task
import java.time.LocalDateTime
import java.util.*

data class TaskExecutionContext(
    val id: UUID,
    val name: String,
    val createdAt: LocalDateTime,
    val status: K8sResourceStatus,
    val startedAt: LocalDateTime?,
    val finishedAt: LocalDateTime?,

    val image: String,
    val command: List<String>,
    val scriptPath: String,
    val script: String,
    val resources: K8sTaskResources,

    val dagRef: UUID,
    val dependsOn: Task? = null,
)