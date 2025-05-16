package com.github.piper.domain.task.kubernetes

import com.github.piper.domain.task.Task
import com.github.piper.kubernetes.crd.TaskResource
import com.github.piper.kubernetes.crd.TaskSpec
import com.github.piper.kubernetes.crd.TaskStatus
import com.github.piper.kubernetes.executors.createTask
import com.github.piper.kubernetes.executors.updateStatus
import com.github.piper.primitives.kubernetes.K8sTaskResources
import java.nio.file.Path

class K8sTask(name: String): Task(name) {
    var image: String? = null
    var script: Path? = null
    var scriptPath: String? = null
    var command: List<String> = emptyList()
    var resources: K8sTaskResources = K8sTaskResources()

    fun resources(block: K8sTaskResources.() -> Unit) {
        resources.apply(block)
    }

    override fun activate(dagRef: String): TaskResource {

        val taskResource = TaskResource().apply {
            spec = TaskSpec(
                name = name,
                image = image!!,
                command = command,
                args = emptyList(),
                env = emptyMap(),
                resources = resources,
                dependsOn = dependsOn?.name,
                dagRef = dagRef
            )
            metadata.generateName = "$name-"
        }

        val status = TaskStatus(status = "awaiting_execution")
        val task = createTask(taskResource).updateStatus(status)

        getChildren().forEach { it.activate(dagRef) }

        return task
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
