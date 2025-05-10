package com.eska.piperweb.adapter.kubernetes

import com.eska.piperweb.adapter.kubernetes.view.JobView
import com.eska.piperweb.core.domain.JobStatus
import com.eska.piperweb.core.usecase.DeleteJobUseCase
import com.eska.piperweb.core.usecase.SaveJobUseCase
import com.eska.piperweb.core.usecase.UpdateJobUseCase
import io.fabric8.kubernetes.api.model.batch.v1.Job
import io.fabric8.kubernetes.client.KubernetesClient
import io.fabric8.kubernetes.client.Watcher
import io.fabric8.kubernetes.client.Watcher.Action
import io.fabric8.kubernetes.client.Watcher.Action.ADDED
import io.fabric8.kubernetes.client.Watcher.Action.BOOKMARK
import io.fabric8.kubernetes.client.Watcher.Action.DELETED
import io.fabric8.kubernetes.client.Watcher.Action.ERROR
import io.fabric8.kubernetes.client.Watcher.Action.MODIFIED
import io.fabric8.kubernetes.client.WatcherException
import java.time.OffsetDateTime
import java.util.*
import javax.annotation.PostConstruct
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component



@Component
class JobMonitor(
    private val client: KubernetesClient,
    private val saveJobUseCase: SaveJobUseCase,
    private val updateJobUseCase: UpdateJobUseCase,
    private val deleteJobUseCase: DeleteJobUseCase
) {
    private val logger = LoggerFactory.getLogger(JobMonitor::class.java)
    private val namespace = client.namespace ?: "default"

    @PostConstruct
    fun watchJobs() {
        logger.info("Starting to watch jobs in namespace: $namespace")
        client.batch().v1()
            .jobs()
            .inNamespace(namespace)
            .watch(object : Watcher<Job> {
                override fun eventReceived(action: Action?, job: Job?) {
                    if (action == null || job == null) return
                    logger.info("Event received: $action for job: ${job.metadata.name}")
                    when (action) {
                        ADDED -> handleJobAdded(job)
                        MODIFIED -> handleJobModified(job)
                        DELETED -> handleJobDeleted(job)
                        ERROR -> logger.error("Error event received for job: ${job.metadata.name}")
                        BOOKMARK -> logger.debug("Bookmark event received for job: ${job.metadata.name}")
                    }
                }

                override fun onClose(cause: WatcherException?) {
                    if (cause != null) {
                        logger.error("Watch closed with exception", cause)
                        // Restart the watch after a delay
                        Thread.sleep(5000)
                        watchJobs()
                    } else {
                        logger.info("Watch closed normally")
                    }
                }
            })
    }

    private fun handleJobAdded(job: Job) {
        val jobView = convertToJobView(job)
        saveJobUseCase.save(jobView)
        logger.info("Job added: ${job.metadata.name}")
    }

    private fun handleJobModified(job: Job) {
        val jobView = convertToJobView(job)
        updateJobUseCase.update(jobView)
        logger.info("Job modified: ${job.metadata.name}")
    }

    private fun handleJobDeleted(job: Job) {
        val jobView = convertToJobView(job)
        deleteJobUseCase.delete(jobView)
        logger.info("Job deleted: ${job.metadata.name}")
    }

    private fun convertToJobView(job: Job): JobView {
        val status = determineJobStatus(job)
        return JobView(
            id = UUID.fromString(job.metadata.uid),
            name = job.metadata.name,
            namespace = job.metadata.namespace,
            creationTimeStamp = job.metadata.creationTimestamp ?: OffsetDateTime.now().toString(),
            startTime = job.status?.startTime ?: OffsetDateTime.now().toString(),
            status = status
        )
    }

    private fun determineJobStatus(job: Job): JobStatus {
        return when {
            job.status?.ready != null && job.status.ready > 0 -> JobStatus.READY
            job.status?.active != null && job.status.active > 0 -> JobStatus.ACTIVE
            job.status?.succeeded != null && job.status.succeeded > 0 -> JobStatus.SUCCEEDED
            job.status?.failed != null && job.status.failed > 0 -> JobStatus.TERMINATING
            else -> JobStatus.UNKNOWN
        }
    }
}
