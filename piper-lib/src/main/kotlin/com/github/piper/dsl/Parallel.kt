package com.github.piper.dsl

import com.github.piper.domain.task.kubernetes.Branch
import com.github.piper.domain.task.kubernetes.K8sParallelTask
import com.github.piper.domain.task.kubernetes.K8sTask
import com.github.piper.domain.dag.Dag
import java.nio.file.Paths

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
    if (task.scriptPath != null) {
        task.script = Paths.get(task.scriptPath!!)
    }
    this.addTask(task)
    return task
}
