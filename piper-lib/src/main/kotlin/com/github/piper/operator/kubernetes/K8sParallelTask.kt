package com.github.piper.operator.kubernetes

import com.github.piper.operator.Task


class K8sParallelTask(val name: String, val branches: List<Branch>): Task(name) {
    override fun execute() {
        log.info { "Starting execution of parallel task '$name'" }
        branches.parallelStream().forEach { branch ->
            branch.getTasks().fold(Unit) { _, task -> (task as K8sTask).execute() }
        }
    }
}

class Branch {
    private val tasks = mutableListOf<Task>()

    fun addTask(task: Task) {
        tasks.add(task)
    }

    fun getTasks(): List<Task> = tasks
}
