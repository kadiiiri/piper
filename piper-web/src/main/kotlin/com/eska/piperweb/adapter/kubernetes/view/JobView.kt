package com.eska.piperweb.adapter.kubernetes.view

import com.eska.piperweb.core.domain.JobStatus
import java.util.*

data class JobView(
    val id: UUID,
    val name: String,
    val namespace: String,
    val creationTimeStamp: String,
    val startTime: String,
    val status: JobStatus
)