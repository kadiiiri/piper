package com.eska.piperweb.adapter.database.entities

import com.eska.piperweb.core.domain.JobStatus
import jakarta.persistence.Entity
import jakarta.persistence.Id
import java.util.*


@Entity
data class Job(
    @Id
    val id: UUID,
    val name: String,
    val namespace: String,
    val creationTimeStamp: String,
    val startTime: String,
    val status: JobStatus,
)

