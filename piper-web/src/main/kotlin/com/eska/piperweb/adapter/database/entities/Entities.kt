package com.eska.piperweb.adapter.database.entities

import com.eska.piperweb.domain.model.ResourceStatus
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime
import java.util.*


@Entity
@Table(name = "dag")
data class DagEntity(
    @Id
    val id: UUID,
    val name: String,
    val createdAt: String,
    val status: ResourceStatus,
    val scheduledFor: LocalDateTime,
)



