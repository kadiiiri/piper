package com.github.piper.operator

import io.github.oshai.kotlinlogging.KLogger
import io.github.oshai.kotlinlogging.KotlinLogging.logger

abstract class Task(val name: String) {
    protected var dependsOn: Task? = null
    protected val log: KLogger = logger {}
    protected val children: MutableList<Task> = mutableListOf()

    fun addChild(task: Task): Task {
        task.dependsOn = this
        children.add(task)
        return task
    }

    open fun logTree(indent: String = "") {
        log.info { "$indent$name" }
        children.forEach { it.logTree("$indent  ") }
    }

    abstract fun activate(): Any

    override fun toString(): String {
        return name
    }
}
