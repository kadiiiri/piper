package com.github.piperweb.domain.service


import com.github.piperweb.application.repository.TaskRepository
import com.github.piperweb.application.usecase.CreateTaskUseCase
import com.github.piperweb.application.usecase.DeleteTaskUseCase
import com.github.piperweb.application.usecase.FindAllTasksUseCase
import com.github.piperweb.application.usecase.FindTaskByIdUseCase
import com.github.piperweb.application.usecase.FindTaskByNameUseCase
import com.github.piperweb.application.usecase.FindTasksByDagUseCase
import com.github.piperweb.application.usecase.UpdateTaskUseCase
import com.github.piperweb.domain.mapper.TaskMapper.toEntity
import com.github.piperweb.domain.mapper.TaskMapper.toModel
import com.github.piperweb.domain.model.Task
import java.util.*
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class TaskService(
    private val taskRepository: TaskRepository
) : FindTaskByIdUseCase,
    FindTaskByNameUseCase,
    FindAllTasksUseCase,
    CreateTaskUseCase,
    DeleteTaskUseCase,
    UpdateTaskUseCase,
    FindTasksByDagUseCase {

    override fun findById(taskId: UUID): Task? = taskRepository.findByIdOrNull(taskId)?.toModel()

    override fun findByName(name: String): Task? = taskRepository.findByName(name)?.toModel()

    override fun findAll(): List<Task> = taskRepository.findAll().map { it.toModel() }

    override fun findByDagId(dagId: UUID): List<Task> = taskRepository.findByDagId(dagId).map { it.toModel() }

    override fun create(task: Task): Task = taskRepository.saveAndFlush(task.toEntity()).toModel()

    override fun delete(name: String) = taskRepository.deleteByName(name)
}