package com.github.piper.kubernetes.pod

import com.github.piper.kubernetes.resources.createConfigMapVolumeSource
import com.github.piper.kubernetes.resources.createVolume
import com.github.piper.kubernetes.resources.createVolumeMount
import io.kubernetes.client.openapi.models.*

data class PodTemplate(
    val podName: String,
    val image: String,
    val command: List<String>,
    val args: List<String>,
    val env: Map<String, String>,
    val volumeName: String,
    val volumeMountPath: String,
    val configMapVolumeSourceName: String,
    val restartPolicy: String,
)

fun PodTemplate.build(): V1PodTemplateSpec {
    return V1PodTemplateSpec().apply {
        metadata = V1ObjectMeta().generateName(podName)
        spec = V1PodSpec().apply {
            containers = listOf(V1Container().apply {
                name = "$podName-pod-runner"
                command = this@build.command
                args = this@build.args
                image = this@build.image
                env = this@build.env.map { (key, value) -> V1EnvVar().name(key).value(value) }
                volumeMounts = listOf(
                    createVolumeMount(volumeName, volumeMountPath),
                )
            })
            restartPolicy = this@build.restartPolicy
            volumes = listOf(
                createVolume(volumeName, createConfigMapVolumeSource(configMapVolumeSourceName)),
            )
        }
    }
}