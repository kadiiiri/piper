package com.github.piper.dsl

import com.github.piper.operator.kubernetes.K8sTask
import com.github.piper.pipeline.Pipeline
import java.nio.file.Paths

fun Pipeline.k8sTask(name: String, block: K8sTask.() -> Unit): K8sTask {
    val task = K8sTask(name).apply(block)
    task.scriptPath?.let { task.script = Paths.get(it) }
    addTask(task)
    return task
}
