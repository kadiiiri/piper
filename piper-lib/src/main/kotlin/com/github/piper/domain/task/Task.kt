package com.github.piper.domain.task

import com.github.piper.kubernetes.crd.TaskSpec
import java.util.Collections.unmodifiableList
import org.slf4j.LoggerFactory

abstract class Task(val name: String) {
    var dependsOn: Task? = null
    private val log = LoggerFactory.getLogger(Task::class.java)

    private val children: MutableList<Task> = mutableListOf()

    fun addChild(task: Task): Task {
        task.dependsOn = this
        children.add(task)
        return task
    }

    fun getChildren(): List<Task> = unmodifiableList(children.toList())

    open fun logTree(indent: String = "") {
        log.info("$indent$name")
        children.forEach { it.logTree("$indent  ") }
    }

    abstract fun activate(dagRef: String): Any

    override fun toString(): String {
        return name
    }
}

fun Task.toRef(): TaskRef = TaskRef(name, dependsOn = dependsOn?.name)
fun Task.toSpec(): TaskSpec = TaskSpec(name, dependsOn = dependsOn?.name)