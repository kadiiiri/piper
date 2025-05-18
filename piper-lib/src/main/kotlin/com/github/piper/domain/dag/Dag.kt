package com.github.piper.domain.dag

import com.github.piper.domain.task.Task
import com.github.piper.domain.task.kubernetes.TaskMapper.toRef
import com.github.piper.kubernetes.crd.DAGResource
import com.github.piper.kubernetes.crd.DAGSpec
import com.github.piper.kubernetes.crd.DAGStatus
import com.github.piper.kubernetes.executors.createDAG
import com.github.piper.kubernetes.executors.updateStatus
import com.github.piper.primitives.kubernetes.K8sResourceStatus.AWAITING_EXECUTION
import com.github.piper.primitives.time.Schedule

class Dag(val name: String) {
    var rootTask: Task? = null
    var schedule: Schedule? = null

    fun addTask(task: Task) {
        if (rootTask == null) {
            rootTask = task
        }
    }

    fun activate(): Dag {
        requireNotNull(rootTask) { "No operators defined for this pipeline." }

        val dagResource = DAGResource().apply {
            spec = DAGSpec(
                name = name,
                rootTask = rootTask!!.toRef(),
                schedule = schedule
            )
            metadata.generateName = name
        }

        val status = DAGStatus(
            status = AWAITING_EXECUTION
        )

        val dag = createDAG(dagResource)

        dag.updateStatus(status)

        rootTask!!.activate(dagRef = name)
        return this
    }

    fun visualize(): Dag {
        rootTask?.logTree()
        return this
    }
}
