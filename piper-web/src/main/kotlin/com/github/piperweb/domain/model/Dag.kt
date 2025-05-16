package com.github.piperweb.domain.model

import com.github.piper.primitives.kubernetes.K8sResourceStatus
import java.time.LocalDateTime
import java.util.*

data class Dag(
    val id: UUID,
    val name: String,
    val createdAt: LocalDateTime,
    val status: K8sResourceStatus,
    val scheduledFor: LocalDateTime,
)
