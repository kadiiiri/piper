package com.eska.piperweb.adapter.kubernetes

import com.eska.piperweb.adapter.database.entities.TaskRepository
import com.eska.piperweb.domain.model.ResourceStatus
import com.eska.piperweb.domain.model.Task
import com.eska.piperweb.domain.model.toEntity
import com.github.piper.kubernetes.crd.TaskResource
import io.fabric8.kubernetes.client.KubernetesClient
import io.fabric8.kubernetes.client.informers.ResourceEventHandler
import io.fabric8.kubernetes.client.informers.SharedIndexInformer
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.util.*
import javax.annotation.PostConstruct
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component


@Component
class TaskInformer(
    private val client: KubernetesClient,
    private val taskRepository: TaskRepository
) {
    private val log = LoggerFactory.getLogger(DagInformer::class.java)
    private val informerFactory = client.informers()

    @PostConstruct
    fun setup() {
        setupTaskInformer().run()
    }

    fun setupTaskInformer(): SharedIndexInformer<TaskResource> {

        val taskInformer = informerFactory.sharedIndexInformerFor(
            TaskResource::class.java,
            30*1000.toLong()
        )

        taskInformer.addEventHandler(object : ResourceEventHandler<TaskResource> {
            override fun onAdd(taskResource: TaskResource) {
                log.info ("Task added: ${taskResource.metadata.name} ${taskResource.metadata.namespace} ${taskResource.metadata.resourceVersion}")

                val task = Task(
                    id = UUID.randomUUID(),
                    name = taskResource.spec.name,
                    status = ResourceStatus.fromString(taskResource.status?.status),
                    createdAt = OffsetDateTime.parse(taskResource.metadata.creationTimestamp).toLocalDateTime(),
                    startTime = LocalDateTime.now(),
                    endTime = null,
                    dagId = null,
                    dependsOn = emptyList()
                )

                taskRepository.save(task.toEntity())
            }

            override fun onUpdate(oldTask: TaskResource, newTask: TaskResource) {
                log.info("Task updated: ${newTask.metadata.name} ${newTask.metadata.namespace} ${newTask.metadata.resourceVersion}")
            }

            override fun onDelete(task: TaskResource, deletedFinalStateUnknown: Boolean) {
                log.info("Task deleted: ${task.metadata.name} ${task.metadata.namespace} ${task.metadata.resourceVersion}")
            }
        })

        return taskInformer
    }
}



