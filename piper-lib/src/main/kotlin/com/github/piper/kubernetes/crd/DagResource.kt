package com.github.piper.kubernetes.crd

import com.github.piper.primitives.kubernetes.K8sResourceStatus
import com.github.piper.primitives.kubernetes.K8sResourceStatus.UNKNOWN
import com.github.piper.primitives.time.Schedule
import io.fabric8.kubernetes.client.CustomResource
import io.fabric8.kubernetes.model.annotation.Group
import io.fabric8.kubernetes.model.annotation.Version
import java.time.LocalDateTime


@Group("piper.github.com")
@Version("v1alpha1")
class DAGResource: CustomResource<DAGSpec, DAGStatus>()

data class DAGSpec(
    val name: String = "",
    val schedule: Schedule? = null,
    val createdAt: LocalDateTime? = null,

    val rootTask: TaskRef? = null,
)

data class DAGStatus(val status: K8sResourceStatus = UNKNOWN)



