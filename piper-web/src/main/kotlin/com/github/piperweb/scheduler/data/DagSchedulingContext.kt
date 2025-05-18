package com.github.piperweb.scheduler.data

import com.github.piper.primitives.kubernetes.K8sResourceStatus
import com.github.piper.primitives.time.Schedule
import com.github.piperweb.domain.model.Dag
import com.github.piperweb.domain.model.Task
import java.time.LocalDateTime
import java.util.*

data class DagSchedulingContext(
    val id: UUID,
    val name: String,
    val createdAt: LocalDateTime,
    val status: K8sResourceStatus,
    val schedule: Schedule,

    val tasks: List<Task> = emptyList()
)

fun Dag.toContext(tasks: List<Task>)= DagSchedulingContext(
    id = id,
    name = name,
    createdAt = createdAt,
    status = status,
    schedule = schedule,
    tasks = tasks
)