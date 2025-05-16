package com.github.piper.kubernetes.crd

import com.github.piper.domain.task.TaskRef
import io.fabric8.kubernetes.client.CustomResource
import io.fabric8.kubernetes.model.annotation.Group
import io.fabric8.kubernetes.model.annotation.Version


@Group("piper.eska.com")
@Version("v1alpha1")
class DAGResource: CustomResource<DAGSpec, DAGStatus>()

data class DAGSpec(val name: String = "", val rootTask: TaskRef? = null)
data class DAGStatus(val status: String = "")



