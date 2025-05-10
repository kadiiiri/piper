package com.eska.piperweb.core

import com.eska.piperweb.adapter.database.entities.OperatorEntity
import com.eska.piperweb.core.domain.Operator

object OperatorMapper {
    fun Operator.toEntity(): OperatorEntity = OperatorEntity(
        id = id,
        name = name,
        children = children.map { it.toEntity() }
    )

    fun OperatorEntity.toDomain(): Operator = Operator(
        id = id,
        name = name,
        children = children.map { it.toDomain() }
    )

    fun List<Operator>.toEntities(): List<OperatorEntity> = this.map { it.toEntity() }

    fun List<OperatorEntity>.toDomains(): List<Operator> = this.map { it.toDomain() }
}