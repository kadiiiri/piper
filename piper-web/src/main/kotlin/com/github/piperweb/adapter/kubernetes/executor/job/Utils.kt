package com.github.piperweb.adapter.kubernetes.executor.job

import com.github.piper.kubernetes.KubernetesDefaults.NAMESPACE
import io.fabric8.kubernetes.api.model.ConfigMap
import io.fabric8.kubernetes.api.model.ConfigMapVolumeSource
import io.fabric8.kubernetes.api.model.ObjectMetaBuilder
import io.fabric8.kubernetes.api.model.Volume
import io.fabric8.kubernetes.api.model.VolumeMount

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

fun createConfigMap(name: String, script: String, scriptPath: String): ConfigMap {
    return ConfigMap().apply {
        metadata = ObjectMetaBuilder()
            .withName(name)
            .withNamespace(NAMESPACE)
            .build()
        data = hashMapOf(Pair(scriptPath.split("/").last(), script))
    }
}
