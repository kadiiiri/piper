package com.github.piper.kubernetes.crd

import com.github.piper.primitives.kubernetes.K8sTaskResources
import io.fabric8.kubernetes.client.CustomResource
import io.fabric8.kubernetes.model.annotation.Group
import io.fabric8.kubernetes.model.annotation.Version


@Group("piper.github.com")
@Version("v1alpha1")
class TaskResource: CustomResource<TaskSpec, TaskStatus>()

data class TaskSpec(
    val name: String = "",
    val image: String = "",
    val command: List<String> = emptyList(),
    val args: List<String> = emptyList(),
    val env: Map<String, String> = emptyMap(),
    val resources: K8sTaskResources = K8sTaskResources(),
    val dagRef: String? = null,
    val dependsOn: String? = null
)


class TaskStatus(
    val status: String = ""
)
