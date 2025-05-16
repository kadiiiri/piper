package com.github.piper.domain.dag

import com.github.piper.domain.task.Task
import com.github.piper.domain.task.toRef
import com.github.piper.kubernetes.crd.DAGResource
import com.github.piper.kubernetes.crd.DAGSpec
import com.github.piper.kubernetes.crd.DAGStatus
import com.github.piper.kubernetes.executors.createDAG
import com.github.piper.kubernetes.executors.updateStatus
import java.time.Duration

class Dag(val name: String) {
    var rootTask: Task? = null
    var timeout: Duration? = null
    var retries: Int = 0

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
            )
            metadata.generateName = name
        }

        val status = DAGStatus(
            status = "awaiting_execution"
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
