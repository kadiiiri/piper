package com.github.piper.kubernetes.resources

import io.kubernetes.client.openapi.models.V1ConfigMapVolumeSource
import io.kubernetes.client.openapi.models.V1Volume
import io.kubernetes.client.openapi.models.V1VolumeMount

fun createVolume(name: String, configMap: V1ConfigMapVolumeSource): V1Volume {
    return V1Volume().apply {
        this.name = name
        this.configMap = configMap
    }
}

fun createConfigMapVolumeSource(name: String): V1ConfigMapVolumeSource {
    return V1ConfigMapVolumeSource().apply {
        this.name = name
        defaultMode = 511
    }
}

fun createVolumeMount(name: String, mountPath: String): V1VolumeMount {
    return V1VolumeMount().apply {
        this.name = name
        this.mountPath = mountPath
    }
}
