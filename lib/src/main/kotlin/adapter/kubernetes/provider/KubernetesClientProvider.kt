package adapter.kubernetes.provider

import io.kubernetes.client.openapi.ApiClient
import io.kubernetes.client.openapi.apis.BatchV1Api
import io.kubernetes.client.openapi.apis.CoreV1Api
import io.kubernetes.client.util.Config.defaultClient

class KubernetesClientProvider {
    private val client: ApiClient = defaultClient()

    val batchApi: BatchV1Api by lazy { BatchV1Api(client) }
    val coreApi: CoreV1Api by lazy { CoreV1Api(client) }
}