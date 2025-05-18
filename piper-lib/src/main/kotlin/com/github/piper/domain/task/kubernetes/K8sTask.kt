package com.github.piper.domain.task.kubernetes

import com.github.piper.domain.task.Task
import com.github.piper.kubernetes.crd.TaskResource
import com.github.piper.kubernetes.crd.TaskSpec
import com.github.piper.kubernetes.crd.TaskStatus
import com.github.piper.kubernetes.executors.createTask
import com.github.piper.kubernetes.executors.updateStatus
import com.github.piper.primitives.kubernetes.K8sResourceStatus.AWAITING_EXECUTION
import com.github.piper.primitives.kubernetes.K8sTaskResources
import java.io.File

class K8sTask(name: String): Task(name) {
    var image: String? = null
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
                script = File(scriptPath!!).readText(),
                scriptPath = scriptPath,
                env = emptyMap(),
                resources = resources,
                dependsOn = dependsOn?.name,
                dagRef = dagRef
            )
            metadata.generateName = "$name-"
        }

        val status = TaskStatus(status = AWAITING_EXECUTION)
        val task = createTask(taskResource).updateStatus(status)

        children.forEach { it.activate(dagRef) }

        return task
    }
}
