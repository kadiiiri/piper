package com.eska.piperweb.adapter.database.entities

import java.util.*
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface DagRepository: JpaRepository<DagEntity, UUID>

@Repository
interface TaskRepository: JpaRepository<TaskEntity, UUID>

//    @Modifying(flushAutomatically = true, clearAutomatically = true)
//    @Query("UPDATE PipelineEntity p SET p.status = 'ENABLED' WHERE p.name = :name AND p.status != 'ENABLED'")
//    fun enable(name: String): Int
//
//    @Modifying(flushAutomatically = true, clearAutomatically = true)
//    @Query("UPDATE PipelineEntity p SET p.status = 'DISABLED' WHERE p.name = :name AND p.status != 'DISABLED'")
//    fun disable(name: String): Int
