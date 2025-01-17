package adapter.kubernetes

import adapter.kubernetes.provider.KubernetesClientProvider
import adapter.kubernetes.resources.ConfigMap
import adapter.kubernetes.run.RunConfig
import io.kubernetes.client.common.KubernetesObject
import java.nio.file.Path

abstract class KubeAdapter(
    namespace: String = "default",
    protected val clientProvider: KubernetesClientProvider = KubernetesClientProvider()
) {
    private val configMap = ConfigMap(clientProvider.coreApi, namespace)

    abstract fun run(
        runConfig: RunConfig,
    ): KubernetesObject

    abstract fun runParallel(
        vararg runConfig: RunConfig,
    ): KubernetesObject

    protected fun createConfigMap(configMapName: String, script: Path) {
        configMap.create(configMapName, script)
    }

    protected fun deleteConfigMap(configMapName: String) {
        configMap.delete(configMapName)
    }
}
