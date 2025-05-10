package com.eska.piperweb.core.domain

import java.util.*

enum class JobStatus {
    ACTIVE, READY, SUCCEEDED, TERMINATING, UNKNOWN
}

enum class PipelineStatus {
    ENABLED, DISABLED, UNKNOWN
}

data class Job(
    val id: UUID,
    val name: String,
    val namespace: String,
    val createdAt: String,
    val startTime: String,
    val status: JobStatus,
)


data class Pipeline(
    val id: UUID,
    val name: String,
    val createdAt: String,
    val status: PipelineStatus,
    val operator: Operator
)


data class Operator(
    val id: UUID,
    val name: String,
    val children: List<Operator>
)


