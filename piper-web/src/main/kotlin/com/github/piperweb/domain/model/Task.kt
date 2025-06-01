package com.github.piperweb.domain.model

import com.github.piper.primitives.kubernetes.K8sResourceStatus
import com.github.piper.primitives.kubernetes.K8sResourceStatus.DONE
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

    val image: String,
    val command: List<String>,
    val scriptPath: String,
    val script: String,

    val resources: K8sTaskResources,

    val dagRef: UUID,
    val dependsOn: Task? = null,
) {
    fun canExecute(): Boolean = dependsOn == null || dependsOn.status == DONE
}
