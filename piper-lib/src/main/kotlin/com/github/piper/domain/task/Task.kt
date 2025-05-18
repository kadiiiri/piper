package com.github.piper.domain.task

import org.slf4j.LoggerFactory

abstract class Task(val name: String) {
    private val log = LoggerFactory.getLogger(Task::class.java)

    val children: MutableList<Task> = mutableListOf()

    var dependsOn: Task? = null
        private set


    fun addChild(task: Task): Task {
        task.dependsOn = this
        children.add(task)
        return task
    }

    open fun logTree(indent: String = "") {
        log.info("$indent$name")
        children.forEach { it.logTree("$indent  ") }
    }

    abstract fun activate(dagRef: String): Any

    override fun toString(): String {
        return name
    }
}
