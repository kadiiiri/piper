package com.github.piperweb.application.usecase

import com.github.piperweb.domain.model.Task
import java.util.*

interface CreateTaskUseCase {
    fun create(task: Task): Task
}

interface UpdateTaskUseCase {
    fun create(task: Task): Task
}

interface DeleteTaskUseCase {
    fun delete(name: String)
}

interface FindTaskByIdUseCase {
    fun findById(taskId: UUID): Task?
}

interface FindTaskByNameUseCase {
    fun findByName(name: String): Task?
}

interface FindAllTasksUseCase {
    fun findAll(): List<Task>
}


