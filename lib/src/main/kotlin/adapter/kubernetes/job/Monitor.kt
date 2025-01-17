package adapter.kubernetes.job

import io.kubernetes.client.openapi.ApiException
import io.kubernetes.client.openapi.apis.BatchV1Api
import kotlinx.coroutines.delay

class JobMonitor(private val batchApi: BatchV1Api, private val namespace: String) {
    suspend fun awaitCompletion(resourceName: String) {
        println("Waiting for completion of job with name '$resourceName'")
        val retryInterval = 3000L // 3 seconds

        while (true) {
            val jobStatus = try {
                batchApi.readNamespacedJob(resourceName, namespace).execute().status
            } catch (e: ApiException) {
                println("Exception when reading Job '$resourceName' status: ${e.responseBody}")
                throw e
            }

            when {
                jobStatus?.succeeded?.let { it > 0 } == true -> {
                    println("Job '$resourceName' succeeded")
                    return
                }
                jobStatus?.failed?.let { it > 0 } == true -> {
                    println("Job '$resourceName' failed")
                    throw IllegalStateException("Job '$resourceName' failed")
                }
                else -> println("Job '$resourceName' is still running...")
            }

            delay(retryInterval)
        }
    }
}