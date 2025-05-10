package com.github.piper.operator

import io.github.oshai.kotlinlogging.KLogger
import io.github.oshai.kotlinlogging.KotlinLogging.logger

abstract class Task(private val id: String) {
    protected var dependsOn: Task? = null
    protected val log: KLogger = logger {}
    protected val children: MutableList<Task> = mutableListOf()

    fun addChild(task: Task): Task {
        task.dependsOn = this
        children.add(task)
        return task
    }

    open fun logTree(indent: String = "") {
        log.info { "$indent$id" }
        children.forEach { it.logTree("$indent  ") }
    }


    /**
     * This is a temporary method to allow the task to be executed. It will be removed once Pipelines and Tasks are registered as CRDs.
     */
    open fun execute() {
        log.info { "Executing task: $id" }
        children.forEach { it.execute() }
    }

    override fun toString(): String {
        return id
    }
}
