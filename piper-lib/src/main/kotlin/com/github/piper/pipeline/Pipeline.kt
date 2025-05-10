package com.github.piper.pipeline

import com.github.piper.operator.Task
import java.time.Duration

class Pipeline(val id: String) {
    var rootTask: Task? = null
    var timeout: Duration? = null
    var retries: Int = 0

    fun addTask(task: Task) {
        if (rootTask == null) {
            rootTask = task
        } else {
            rootTask!!.addChild(task)
        }
    }

    fun activate(): Pipeline {
        requireNotNull(rootTask) { "No operators defined for this pipeline." }
        rootTask!!.execute()
        return this
    }

    fun visualize(): Pipeline {
        rootTask?.logTree()
        return this
    }
}
