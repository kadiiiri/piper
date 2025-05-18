package com.github.piperweb.application.repository

import com.github.piperweb.adapter.persistence.entity.TaskEntity
import java.util.*
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TaskRepository: JpaRepository<TaskEntity, UUID> {
    fun findByName(name: String): TaskEntity?
    fun findByDagId(dagId: UUID): List<TaskEntity>
    fun deleteByName(name: String)
}
