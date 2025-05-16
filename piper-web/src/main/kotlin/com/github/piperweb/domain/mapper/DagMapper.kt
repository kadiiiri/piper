package com.github.piperweb.domain.mapper

import com.github.piperweb.adapter.database.entity.DagEntity
import com.github.piperweb.domain.model.Dag

object DagMapper {
    fun Dag.toEntity() = DagEntity(id, name, createdAt, status, scheduledFor)

    fun DagEntity.toModel() = Dag(id!!, name, createdAt, status, scheduledFor)
}