package com.github.piper.kubernetes.resources

import com.github.piper.kubernetes.KubernetesDefaults.NAMESPACE
import io.fabric8.kubernetes.api.model.*
import java.nio.file.Path

fun createVolume(name: String, configMap: ConfigMapVolumeSource): Volume {
    return Volume().apply {
        this.name = name
        this.configMap = configMap
    }
}

fun createConfigMapVolumeSource(name: String): ConfigMapVolumeSource {
    return ConfigMapVolumeSource().apply {
        this.name = name
        defaultMode = 511
    }
}

fun createVolumeMount(name: String, mountPath: String): VolumeMount {
    return VolumeMount().apply {
        this.name = name
        this.mountPath = mountPath
    }
}

fun createConfigMap(name: String, script: Path): ConfigMap {
    return ConfigMap().apply {
        metadata = ObjectMetaBuilder()
            .withName(name)
            .withNamespace(NAMESPACE)
            .build()
        data = hashMapOf(Pair(script.fileName.toString(), script.toFile().readText()))
    }
}