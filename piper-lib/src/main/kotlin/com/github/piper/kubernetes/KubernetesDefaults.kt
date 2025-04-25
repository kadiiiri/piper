package com.github.piper.kubernetes

object KubernetesDefaults {
    const val API_VERSOON: String = "batch/v1"
    const val RESTART_POLICY: String = "Never"
    const val MEMORY: String = "memory"
    const val CPU: String = "cpu"
    const val MI: String = "Mi"
    const val JOB_TIL_SECONDS: Int = 300
    const val NAMESPACE = "default"
    const val BACKOFF_LIMIT: Int = 0
    const val DEFAULT_VOLUME_NAME: String = "script-volume"
    const val DEFAULT_VOLUME_PATH: String = "/scripts"
    const val DEFAULT_CONFIGMAP_VOLUME_SOURCE_SUFFIX: String = "-script-config"
    val DEFAULT_ENV: Map<String, String> = mapOf("KOTLIN_HOME" to "/opt/kotlinc")
    const val EXIT_CODE_SUCCESS = 0
    const val EXIT_CODE_FAILURE = -1
    const val RUNNING_CODE = 1;
}