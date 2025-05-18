package com.github.piperweb.adapter.kubernetes.executor.job

import com.github.piper.kubernetes.KubernetesDefaults.API_VERSOON
import com.github.piper.kubernetes.KubernetesDefaults.BACKOFF_LIMIT
import com.github.piper.kubernetes.KubernetesDefaults.CPU
import com.github.piper.kubernetes.KubernetesDefaults.DEFAULT_CONFIGMAP_VOLUME_SOURCE_SUFFIX
import com.github.piper.kubernetes.KubernetesDefaults.DEFAULT_ENV
import com.github.piper.kubernetes.KubernetesDefaults.DEFAULT_VOLUME_NAME
import com.github.piper.kubernetes.KubernetesDefaults.DEFAULT_VOLUME_PATH
import com.github.piper.kubernetes.KubernetesDefaults.MEMORY
import com.github.piper.kubernetes.KubernetesDefaults.MI
import com.github.piper.kubernetes.KubernetesDefaults.NAMESPACE
import com.github.piper.kubernetes.KubernetesDefaults.RESTART_POLICY
import io.fabric8.kubernetes.api.model.AffinityBuilder
import io.fabric8.kubernetes.api.model.EnvVar
import io.fabric8.kubernetes.api.model.LocalObjectReference
import io.fabric8.kubernetes.api.model.NodeSelectorRequirement
import io.fabric8.kubernetes.api.model.NodeSelectorTerm
import io.fabric8.kubernetes.api.model.Quantity
import io.fabric8.kubernetes.api.model.ResourceRequirements
import io.fabric8.kubernetes.api.model.batch.v1.Job
import io.fabric8.kubernetes.api.model.batch.v1.JobBuilder

data class ExecutorRequest(
    val name: String,

    // image
    val image: String,
    val pullSecret: String? = null,
    val command: List<String>,
    val env: Map<String, String> = emptyMap(),
    val script: String,
    val scriptPath: String,

    // Resources
    val minCpuCores: Double,
    val minMemory: Double,
    val maxCpuCores: Double,
    val maxMemory: Double,

    // Volume
    val volumeName: String = DEFAULT_VOLUME_NAME,
    val volumeMountPath: String = DEFAULT_VOLUME_PATH,
    val configMapVolumeSourceName: String = "${name}${DEFAULT_CONFIGMAP_VOLUME_SOURCE_SUFFIX}",

    val nodeSelectorRequirements: List<NodeSelectorRequirement> = emptyList()
)


fun ExecutorRequest.build(): Job {

    val requestResources = HashMap<String, Quantity>()
        .putMemoryMi(minMemory)
        .putCpuCores(minCpuCores)


    val limitResources = HashMap<String, Quantity>()
        .putMemoryMi(maxMemory)
        .putCpuCores(maxCpuCores)

    val affinity = if (nodeSelectorRequirements.isEmpty()) null else AffinityBuilder()
        .withNewNodeAffinity()
        .withNewRequiredDuringSchedulingIgnoredDuringExecution()
        .addNewNodeSelectorTermLike(NodeSelectorTerm().apply { matchExpressions = nodeSelectorRequirements })
        .endNodeSelectorTerm()
        .endRequiredDuringSchedulingIgnoredDuringExecution()
        .endNodeAffinity().build()


    return JobBuilder()
        .withApiVersion(API_VERSOON)
        .withNewMetadata()
        .withName(name)
        .withLabels<String, String>(HashMap<String, String>())
        .withNamespace(NAMESPACE)
        .endMetadata()
        .withNewSpec()
        .withTtlSecondsAfterFinished(1000)
        .withNewTemplate()
        .withNewMetadata()
        .withLabels<String, String>(HashMap<String, String>())
        .endMetadata()
        .withNewSpec()
        .addNewContainer()
        .withName(name)
        .withImage(image)
        .withCommand(command)
        .withArgs(listOf(volumeMountPath + "/" + scriptPath.split("/").last()))
        .withVolumeMounts(createVolumeMount(volumeName, volumeMountPath))
        .withImagePullPolicy(pullSecret)
        .withResources(ResourceRequirements().apply {
            limits = limitResources
            requests = requestResources
        })
        .withEnv((DEFAULT_ENV + env).createEnv())
        .endContainer()
        .withImagePullSecrets(LocalObjectReference()) // TODO: How to fill this in for now?
        .withRestartPolicy(RESTART_POLICY)
        .withAffinity(affinity)
        .withVolumes(createVolume(volumeName, createConfigMapVolumeSource(configMapVolumeSourceName)))
        .endSpec()
        .endTemplate()
        .withBackoffLimit(BACKOFF_LIMIT)
        .endSpec()
        .build()
}


fun HashMap<String, Quantity>.putMemoryMi(memory: Double): HashMap<String, Quantity> {
    put(MEMORY, Quantity("${memory}${MI}"))
    return this
}

fun HashMap<String, Quantity>.putCpuCores(cpu: Double): HashMap<String, Quantity> {
    put(CPU, Quantity(cpu.toString()))
    return this
}

fun Map<String, String>.createEnv(): List<EnvVar> {
    return this.map { (k, v) -> EnvVar(k, v, null)}
}