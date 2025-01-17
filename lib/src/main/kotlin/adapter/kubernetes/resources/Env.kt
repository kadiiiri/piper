package adapter.kubernetes.resources

import io.kubernetes.client.openapi.models.V1EnvVar

fun createEnv(name: String, value: String): V1EnvVar {
    return V1EnvVar().apply {
        this.name = name
        this.value = value
    }
}

fun createEnv(envVars: Map<String, String>): List<V1EnvVar> {
    return envVars.map { createEnv(it.key, it.value) }
}