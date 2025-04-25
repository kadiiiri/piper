package com.eska.piperweb.adapter.database.entities

import java.util.UUID
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface JobDataRepository: JpaRepository<Job, UUID> {
    fun findByName(string: String): Job?
}