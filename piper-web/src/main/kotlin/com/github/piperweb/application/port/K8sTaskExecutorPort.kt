package com.github.piperweb.application.port

import com.github.piperweb.domain.model.Task

interface K8sTaskExecutorPort {
    fun execute(task: Task)
}