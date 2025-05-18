package com.github.piperweb.application.repository

import com.github.piperweb.adapter.persistence.entity.DagEntity
import java.util.*
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface DagRepository: JpaRepository<DagEntity, UUID> {
    fun findByName(name: String): DagEntity?
    fun deleteByName(name: String)
}