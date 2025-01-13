package adapter.kubernetes

import io.kubernetes.client.openapi.models.V1Container
import io.kubernetes.client.openapi.models.V1ObjectMeta
import io.kubernetes.client.openapi.models.V1Pod
import io.kubernetes.client.openapi.models.V1PodSpec
import java.nio.file.Path

class KubePodAdapter(namespace: String = "default") : KubeAdapter(namespace) {

    override fun run(resourceName: String, image: String, command: List<String>, args: List<String>, script: Path): V1Pod {
        deleteNamespacedConfigMap(resourceName)
        createNamespacedConfigMap(resourceName, script)
        return createNamespacedPod(resourceName, command, args, mapOf(Pair("KOTLIN_HOME", "/opt/kotlinc")))
    }

    private fun createNamespacedPod(podName: String, command: List<String>, args: List<String>, env: Map<String, String>): V1Pod {
        val body = V1Pod().apply {
            metadata = V1ObjectMeta().apply { generateName = "$podName-" }
            spec = V1PodSpec().apply {
                containers = listOf(V1Container().apply {
                    this.command = command
                    this.image = image
                    this.args = args
                    this.env = createV1EnvVars(env)
                    volumeMounts = listOf(createV1VolumeMount())
                })
            }

        }
        return coreApi.createNamespacedPod(namespace, body)
            .execute()
    }
}