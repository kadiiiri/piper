package com.eska.piperweb.api.monitor

import com.eska.piperweb.adapter.database.entities.TaskEntity
import com.eska.piperweb.adapter.database.entities.TaskRepository
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/task")
class TaskController(
    private val taskRepository: TaskRepository
) {

    @RequestMapping("/list")
    fun list(): List<TaskEntity> = taskRepository.findAll()
}