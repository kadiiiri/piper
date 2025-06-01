package com.github.piperweb.domain.model

import com.github.piper.primitives.kubernetes.K8sResourceStatus
import com.github.piper.primitives.kubernetes.K8sResourceStatus.AWAITING_EXECUTION
import com.github.piper.primitives.time.Schedule
import java.time.LocalDateTime
import java.util.*

data class Dag(
    val id: UUID,
    val name: String,
    val createdAt: LocalDateTime,
    val status: K8sResourceStatus,
    val schedule: Schedule,
) {
    fun canBeScheduled(): Boolean {
        if (status != AWAITING_EXECUTION) return false
//        if (schedule.nextExecutionTime()?.isAfter(OffsetDateTime.now()) == false) return false
        return true
    }
}
