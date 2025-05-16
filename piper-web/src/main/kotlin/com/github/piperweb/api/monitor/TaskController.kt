package com.github.piperweb.api.monitor

import com.github.piperweb.domain.model.Task
import com.github.piperweb.domain.usecase.FindAllTasksUseCase
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