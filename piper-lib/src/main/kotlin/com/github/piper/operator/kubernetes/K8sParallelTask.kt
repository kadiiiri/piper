package com.github.piper.operator.kubernetes

import com.github.piper.operator.Task


class K8sParallelTask(name: String, private val branches: List<Branch>): Task(name) {
    override fun activate() {
        log.info { "Activating parallel task '$name'" }
        branches.forEach { branch -> branch.getTasks().forEach { it.activate() } }
    }
}

class Branch {
    private val tasks = mutableListOf<Task>()

    fun addTask(task: Task) {
        tasks.add(task)
    }

    fun getTasks(): List<Task> = tasks
}
