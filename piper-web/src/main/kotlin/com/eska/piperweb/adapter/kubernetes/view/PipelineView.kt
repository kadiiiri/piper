package com.eska.piperweb.adapter.kubernetes.view

import java.util.*

data class PipelineView(
    val id: UUID,
    val name: String,
    val createdAt: String,
)