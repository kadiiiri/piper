package com.github.piper.domain.task.kubernetes

import com.github.piper.domain.task.Task
import com.github.piper.kubernetes.crd.TaskRef

object TaskMapper {
    fun Task.toRef(): TaskRef = TaskRef(name, dependsOn = dependsOn?.name)
}