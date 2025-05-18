package com.github.piperweb.adapter.database.repository

import com.github.piperweb.adapter.database.entity.DagEntity
import com.github.piperweb.adapter.database.entity.TaskEntity
import java.util.*
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface DagRepository: JpaRepository<DagEntity, UUID> {
    fun findByName(name: String): DagEntity?
    fun deleteByName(name: String)
}

@Repository
interface TaskRepository: JpaRepository<TaskEntity, UUID> {
    fun findByName(name: String): TaskEntity?
    fun findByDagId(dagId: UUID): List<TaskEntity>
    fun deleteByName(name: String)
}

//    @Modifying(flushAutomatically = true, clearAutomatically = true)
//    @Query("UPDATE PipelineEntity p SET p.status = 'ENABLED' WHERE p.name = :name AND p.status != 'ENABLED'")
//    fun enable(name: String): Int
//
//    @Modifying(flushAutomatically = true, clearAutomatically = true)
//    @Query("UPDATE PipelineEntity p SET p.status = 'DISABLED' WHERE p.name = :name AND p.status != 'DISABLED'")
//    fun disable(name: String): Int
