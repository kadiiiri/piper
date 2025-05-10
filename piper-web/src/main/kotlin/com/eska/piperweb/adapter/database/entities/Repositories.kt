package com.eska.piperweb.adapter.database.entities

import java.util.*
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface JobDataRepository: JpaRepository<Job, UUID> {
    fun findByName(name: String): Job?
}

@Repository
interface PipelineDataRepository: JpaRepository<PipelineEntity, UUID> {
    fun findByName(name: String): PipelineEntity?

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("UPDATE PipelineEntity p SET p.status = 'ENABLED' WHERE p.name = :name AND p.status != 'ENABLED'")
    fun enable(name: String): Int

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("UPDATE PipelineEntity p SET p.status = 'DISABLED' WHERE p.name = :name AND p.status != 'DISABLED'")
    fun disable(name: String): Int

    fun deleteByName(name: String)
}

@Repository
interface OperatorDataRepository: JpaRepository<OperatorEntity, UUID> {
    fun findByName(string: String): OperatorEntity?
}
