package adapter.kubernetes.job

import io.github.oshai.kotlinlogging.KotlinLogging
import io.kubernetes.client.openapi.ApiException
import io.kubernetes.client.openapi.apis.BatchV1Api
import kotlinx.coroutines.delay
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.toDuration


class JobMonitor(private val batchApi: BatchV1Api, private val namespace: String) {
    private val logger = KotlinLogging.logger {}

    suspend fun awaitCompletion(
        resourceName: String,
        retryInterval: Duration = 3000L.toDuration(DurationUnit.MILLISECONDS),
    ) {

        logger.info { "Waiting for completion of job with name '$resourceName'" }

        while (true) {
            val jobStatus = try {
                batchApi.readNamespacedJob(resourceName, namespace).execute().status
            } catch (e: ApiException) {
                logger.error {"Exception when reading Job '$resourceName' status: ${e.responseBody}"}
                throw IllegalStateException("Job '$resourceName' failed with status: ${e.responseBody}", e)
            }

            when {
                jobStatus?.succeeded?.let { it > 0 } == true -> {
                    logger.info { "Job '$resourceName' succeeded" }
                    return
                }
                jobStatus?.failed?.let { it > 0 } == true -> {
                    logger.info { "Job '$resourceName' failed" }
                    return
                }
            }

            delay(retryInterval)
        }
    }
}