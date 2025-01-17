package adapter.kubernetes.resources

import io.kubernetes.client.openapi.ApiException
import io.kubernetes.client.openapi.apis.CoreV1Api
import io.kubernetes.client.openapi.models.V1ConfigMap
import io.kubernetes.client.openapi.models.V1ObjectMeta
import java.nio.file.Path

class ConfigMap(private val coreApi: CoreV1Api, private val namespace: String) {
    fun create(configMapName: String, script: Path) {
        val configMap = V1ConfigMap()
            .metadata(V1ObjectMeta().name(configMapName))
            .putDataItem(script.fileName.toString(), script.toFile().readText())

        try {
            coreApi.createNamespacedConfigMap(namespace, configMap).execute()
        } catch (e: ApiException) {
            println("ConfigMap already exists: ${e.responseBody}")
        }
    }

    fun delete(configMapName: String) {
        try {
            coreApi.deleteNamespacedConfigMap(configMapName, namespace).execute()
        } catch (e: ApiException) {
            println("ConfigMap does not exist: ${e.responseBody}")
        }
    }
}