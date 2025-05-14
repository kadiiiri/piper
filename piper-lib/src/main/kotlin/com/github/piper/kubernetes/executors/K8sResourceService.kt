package com.github.piper.kubernetes.executors

import com.github.piper.kubernetes.crd.DAGResource
import com.github.piper.kubernetes.crd.TaskResource
import io.fabric8.kubernetes.client.KubernetesClientBuilder

fun createDAG(resource: DAGResource): DAGResource = KubernetesClientBuilder()
    .build()
    .resource(resource)
    .create()

fun createTask(resource: TaskResource): TaskResource = KubernetesClientBuilder()
    .build()
    .resource(resource)
    .create()