package com.github.piper.domain.task.kubernetes

import com.github.piper.domain.task.Task
import org.slf4j.LoggerFactory


class K8sParallelTask(name: String, private val branches: List<Branch>): Task(name) {
    private val log = LoggerFactory.getLogger(K8sParallelTask::class.java)
    override fun activate(dagRef: String) {
        log.info("Activating parallel task '$name'")
        branches.forEach { branch -> branch.getTasks().forEach { it.activate(dagRef) } }
    }
}

class Branch {
    private val tasks = mutableListOf<Task>()

    fun addTask(task: Task) {
        tasks.add(task)
    }

    fun getTasks(): List<Task> = tasks
}
