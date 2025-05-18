package com.github.piper.dsl

import com.github.piper.domain.dag.Dag
import com.github.piper.domain.task.kubernetes.Branch
import com.github.piper.domain.task.kubernetes.K8sParallelTask
import com.github.piper.domain.task.kubernetes.K8sTask

class ParallelContext {
    val branches = mutableListOf<Branch>()

    fun branch(block: Branch.() -> Unit) {
        val branch = Branch().apply(block)
        branches.add(branch)
    }
}

fun Dag.k8sParallelTask(block: ParallelContext.() -> Unit): K8sParallelTask {
    val context = ParallelContext().apply(block)
    val parallelTask = K8sParallelTask("parallel", context.branches)
    if (rootTask == null) addTask(parallelTask)
    return parallelTask
}


fun Branch.k8sTask(name: String, block: K8sTask.() -> Unit): K8sTask {
    val task = K8sTask(name).apply(block)
    this.addTask(task)
    return task
}
