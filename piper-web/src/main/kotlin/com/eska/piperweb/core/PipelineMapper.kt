package com.eska.piperweb.core

import com.eska.piperweb.adapter.database.entities.PipelineEntity
import com.eska.piperweb.core.OperatorMapper.toDomain
import com.eska.piperweb.core.OperatorMapper.toEntity
import com.eska.piperweb.core.domain.Pipeline

object PipelineMapper {
    fun Pipeline.toEntity() = PipelineEntity(
        id = id,
        name = name,
        createdAt = createdAt,
        status = status,
        operator = operator.toEntity()
    )

    fun PipelineEntity.toDomain() = Pipeline(
        id = id,
        name = name,
        createdAt = createdAt,
        status = status,
        operator = operator.toDomain(),
    )

    fun List<PipelineEntity>.toDomains() = this.map { it.toDomain() }

    fun List<Pipeline>.toEntities() = this.map { it.toEntity() }
}