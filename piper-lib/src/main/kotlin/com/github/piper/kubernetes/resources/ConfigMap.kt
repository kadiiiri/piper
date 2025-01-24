package com.github.piper.kubernetes.resources

import io.github.oshai.kotlinlogging.KotlinLogging
import io.kubernetes.client.openapi.ApiException
import io.kubernetes.client.openapi.apis.CoreV1Api
import io.kubernetes.client.openapi.models.V1ConfigMap
import io.kubernetes.client.openapi.models.V1ObjectMeta
import java.nio.file.Path

class ConfigMap(private val coreApi: CoreV1Api, private val namespace: String) {
    private val logger = KotlinLogging.logger {}
    fun create(configMapName: String, script: Path) {
        val configMap = V1ConfigMap()
            .metadata(V1ObjectMeta().name(configMapName))
            .putDataItem(script.fileName.toString(), script.toFile().readText())

        try {
            coreApi.createNamespacedConfigMap(namespace, configMap).execute()
        } catch (e: ApiException) {
            logger.info { "ConfigMap already exists: ${e.responseBody}" }
        }
    }

    fun delete(configMapName: String) {
        try {
            coreApi.deleteNamespacedConfigMap(configMapName, namespace).execute()
        } catch (e: ApiException) {
            logger.info { "ConfigMap does not exist: ${e.responseBody}" }
        }
    }
}