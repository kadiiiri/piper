package adapter.kubernetes

import io.kubernetes.client.openapi.models.*
import java.nio.file.Path


class KubeJobAdapter(namespace: String = "default") : KubeAdapter(namespace) {
    override fun run(resourceName: String, image: String, command: List<String>, args: List<String>, script: Path): V1Job {
        deleteNamespacedConfigMap(resourceName)
        createNamespacedConfigMap(resourceName, script)
        return createNamespacedJob(resourceName, image, command, args, mapOf(Pair("KOTLIN_HOME", "/opt/kotlinc")))
    }

    private fun createNamespacedJob(name: String, image: String, command: List<String>, args: List<String>, env: Map<String, String>): V1Job {
        val body = V1Job().apply {
            metadata = V1ObjectMeta().apply { this.name = name }
            spec = V1JobSpec().apply {
                backoffLimit = 3
                template = createV1PodTemplateSpec(name, image, command, args, env)
            }
        }
        return batchApi.createNamespacedJob(namespace, body).execute()
    }

    private fun createV1PodTemplateSpec(podName: String, image: String, command: List<String>, args: List<String>, envVars: Map<String, String>): V1PodTemplateSpec {
        return V1PodTemplateSpec().apply {
            metadata = V1ObjectMeta().generateName(podName)
            spec = V1PodSpec().apply {
                containers = listOf(V1Container().apply {
                    this.name = "$podName-pod-runner"
                    this.command = command
                    this.args = args
                    this.image = image
                    env = createV1EnvVars(envVars)
                    volumeMounts = listOf(createV1VolumeMount())
                })
                restartPolicy = "Never"
                volumes = listOf(createV1Volume(podName))
            }
        }
    }
}

