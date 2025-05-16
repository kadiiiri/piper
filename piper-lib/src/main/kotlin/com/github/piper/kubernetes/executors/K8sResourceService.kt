package com.github.piper.kubernetes.executors

import com.github.piper.kubernetes.crd.DAGResource
import com.github.piper.kubernetes.crd.DAGStatus
import com.github.piper.kubernetes.crd.TaskResource
import com.github.piper.kubernetes.crd.TaskStatus
import io.fabric8.kubernetes.client.KubernetesClientBuilder

fun createDAG(resource: DAGResource): DAGResource = KubernetesClientBuilder()
    .build()
    .resource(resource)
    .create()

fun createTask(resource: TaskResource): TaskResource = KubernetesClientBuilder()
    .build()
    .resource(resource)
    .create()

fun TaskResource.updateStatus(newStatus: TaskStatus): TaskResource {
    status = newStatus

    return KubernetesClientBuilder()
        .build()
        .resource(this)
        .updateStatus()

}

fun DAGResource.updateStatus(newStatus: DAGStatus): DAGResource {
    status = newStatus

    return KubernetesClientBuilder()
        .build()
        .resource(this)
        .updateStatus()
}
