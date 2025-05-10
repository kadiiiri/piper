package com.github.piper.operator.kubernetes

import com.github.piper.kubernetes.executors.ExecutorRequest
import com.github.piper.kubernetes.executors.K8sExecutor
import com.github.piper.operator.Task
import com.github.piper.util.appendUID
import java.nio.file.Path

class K8sTask(val name: String): Task(name) {
    var image: String? = null
    var script: Path? = null
    var scriptPath: String? = null
    var command: List<String> = emptyList()
    var resources: Resources = Resources()

    fun resources(block: Resources.() -> Unit) {
        resources.apply(block)
    }

    override fun execute() {
        log.info { "Starting execution of task '$name' with image '$image' and script '$script'" }

        if (script != null) {
            val taskName = name.appendUID()

            val executorRequest = ExecutorRequest(
                name = taskName,
                image = image ?: "ubuntu:latest",
                command = command,
                args = listOf("/scripts/${script!!.fileName}"),
                script = script!!,
                minCpuCores = resources.minCpuCores,
                minMemory = resources.minMemory,
                maxCpuCores = resources.maxCpuCores,
                maxMemory = resources.maxMemory
            )

            val k8sExecutor = K8sExecutor(executorRequest)
            k8sExecutor.execute()
        } else {
            log.warn { "Skipping execution of task '$name' because no script is provided" }
        }

        // Execute all child tasks
        children.forEach { it.execute() }
    }
}
