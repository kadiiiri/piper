package com.github.piperweb.application.usecase

import com.github.piperweb.domain.model.Task
import java.util.*

interface FindTasksByDagUseCase {
    fun findByDagId(dagId: UUID): List<Task>
}