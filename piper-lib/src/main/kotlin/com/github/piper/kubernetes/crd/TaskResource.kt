package com.github.piper.kubernetes.crd

import io.fabric8.kubernetes.client.CustomResource
import io.fabric8.kubernetes.model.annotation.Group
import io.fabric8.kubernetes.model.annotation.Version


@Group("piper.eska.com")
@Version("v1alpha1")
class TaskResource: CustomResource<TaskSpec, TaskStatus>()

data class TaskSpec(
    val name: String = "",
    val image: String = "",
    val command: List<String> = emptyList(),
    val args: List<String> = emptyList(),
    val env: Map<String, String> = emptyMap(),
    val dependsOn: List<String> = emptyList(),
)


class TaskStatus(
    val name: String = "",
    val status: String = ""
)
