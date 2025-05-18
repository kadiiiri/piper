package com.github.piperweb.adapter.rest.dashboard.v1

import com.github.piperweb.application.usecase.FindAllTasksUseCase
import com.github.piperweb.domain.model.Task
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/task")
class TaskController(
    private val findAllDagsUseCase: FindAllTasksUseCase
) {

    @RequestMapping("/list")
    fun list(): List<Task> = findAllDagsUseCase.findAll()
}