package com.eska.piperweb.adapter.database.entities

import com.eska.piperweb.core.domain.JobStatus
import com.eska.piperweb.core.domain.PipelineStatus
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType.STRING
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType.LAZY
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import jakarta.validation.constraints.NotBlank
import java.util.*


@Entity
data class Job(
    @Id
    val id: UUID,
    val name: String,
    val namespace: String,
    val createdAt: String,
    val startTime: String,
    val status: JobStatus,
)

@Entity
@Table(name = "pipelines")
data class PipelineEntity(
    @Id
    val id: UUID,

    @NotBlank
    val name: String,

    @Column(nullable = false)
    val createdAt: String,

    @Enumerated(STRING)
    val status: PipelineStatus,

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "operator_id")
    val operator: OperatorEntity
)

@Entity
@Table(name = "operators")
data class OperatorEntity(
    @Id
    val id: UUID,

    @NotBlank
    val name: String,

    @OneToMany(cascade = [CascadeType.ALL], fetch = LAZY)
    @JoinColumn(name = "parent_operator_id")
    val children: List<OperatorEntity> = emptyList()
)

