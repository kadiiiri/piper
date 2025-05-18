package com.github.piper.dsl

import com.github.piper.domain.task.kubernetes.K8sTask
import com.github.piper.domain.dag.Dag
import java.nio.file.Paths

fun Dag.k8sTask(name: String, block: K8sTask.() -> Unit): K8sTask {
    val task = K8sTask(name).apply(block)
    addTask(task)
    return task
}
