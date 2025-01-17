package adapter.kubernetes.job

import adapter.kubernetes.pod.PodTemplate
import adapter.kubernetes.pod.build
import io.kubernetes.client.openapi.models.V1Job
import io.kubernetes.client.openapi.models.V1JobSpec
import io.kubernetes.client.openapi.models.V1ObjectMeta

data class Job(
    val resourceName: String,
    val image: String,
    val command: List<String>,
    val args: List<String>,
    val env: Map<String, String> = emptyMap(),
    val volumeName: String = "script-volume",
    val volumeMountPath: String = "/scripts",
    val configMapVolumeSourceName: String = "$resourceName-script-config",
    val backoffLimit: Int = 3,
    val restartPolicy: String = "Never",
)


fun Job.build(): V1Job {
    return V1Job().apply {
        metadata = V1ObjectMeta().name(resourceName)
        spec = V1JobSpec().apply {
            backoffLimit = this@build.backoffLimit
            template = PodTemplate(
                podName = resourceName,
                image = image,
                command = command,
                args = args,
                env = env,
                volumeName = volumeName,
                volumeMountPath = volumeMountPath,
                configMapVolumeSourceName = configMapVolumeSourceName,
                restartPolicy = restartPolicy,
            ).build()
        }
    }
}

