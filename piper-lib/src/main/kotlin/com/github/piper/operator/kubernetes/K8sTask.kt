package com.github.piper.operator.kubernetes

import com.github.piper.kubernetes.crd.TaskResource
import com.github.piper.kubernetes.crd.TaskSpec
import com.github.piper.kubernetes.crd.TaskStatus
import com.github.piper.kubernetes.executors.createTask
import com.github.piper.operator.Task
import java.nio.file.Path

class K8sTask(name: String): Task(name) {
    var image: String? = null
    var script: Path? = null
    var scriptPath: String? = null
    var command: List<String> = emptyList()
    var resources: Resources = Resources()

    fun resources(block: Resources.() -> Unit) {
        resources.apply(block)
    }

    override fun activate(): TaskResource {

        val taskResource = TaskResource().apply {
            spec = TaskSpec(
                name = name,
                image = image!!,
                command = command,
                args = emptyList(),
                env = emptyMap(),
                dependsOn = if (dependsOn != null) listOf(dependsOn!!.name) else emptyList()
            )
            status = TaskStatus(
                name = "$name-status",
                status = "awaiting_execution"
            )
            metadata.generateName = name
        }

        children.forEach { it.activate() }

        return createTask(taskResource)
    }

//    override fun execute() {
//        log.info { "Starting execution of task '$name' with image '$image' and script '$script'" }
//
//        if (script != null) {
//            val taskName = name.appendUID()
//
//            val executorRequest = ExecutorRequest(
//                name = taskName,
//                image = image ?: "ubuntu:latest",
//                command = command,
//                args = listOf("/scripts/${script!!.fileName}"),
//                script = script!!,
//                minCpuCores = resources.minCpuCores,
//                minMemory = resources.minMemory,
//                maxCpuCores = resources.maxCpuCores,
//                maxMemory = resources.maxMemory
//            )
//
//            val k8sExecutor = K8sExecutor(executorRequest)
//            k8sExecutor.execute()
//        } else {
//            log.warn { "Skipping execution of task '$name' because no script is provided" }
//        }
//
//        // Execute all child tasks
//        children.forEach { it.execute() }
//    }
}
