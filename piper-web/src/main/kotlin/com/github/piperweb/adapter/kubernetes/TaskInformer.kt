package com.github.piperweb.adapter.kubernetes


import com.github.piper.kubernetes.crd.TaskResource
import com.github.piper.primitives.kubernetes.K8sResourceStatus
import com.github.piperweb.domain.model.Task
import com.github.piperweb.domain.usecase.CreateTaskUseCase
import com.github.piperweb.domain.usecase.DeleteTaskUseCase
import com.github.piperweb.domain.usecase.FindDagByNameUseCase
import com.github.piperweb.domain.usecase.FindTaskByNameUseCase
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
    private val createTaskUseCase: CreateTaskUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase,
    private val findDagByNameUseCase: FindDagByNameUseCase,
    private val findTaskByNameUseCase: FindTaskByNameUseCase,
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
                    status = K8sResourceStatus.fromString(taskResource.status?.status),
                    createdAt = OffsetDateTime.parse(taskResource.metadata.creationTimestamp).toLocalDateTime(),
                    startTime = LocalDateTime.now(),
                    finishedAt = null,
                    resources = taskResource.spec.resources,
                    dagId = findDagByNameUseCase.findByName(taskResource.spec.dagRef!!)?.id,
                    dependsOn = if (taskResource.spec.dependsOn != null) findTaskByNameUseCase.findByName(taskResource.spec.dependsOn!!) else null
                )

                createTaskUseCase.create(task)
            }

            override fun onUpdate(oldTask: TaskResource, newTask: TaskResource) {
                log.info("Task updated: ${newTask.metadata.name} ${newTask.metadata.namespace} ${newTask.metadata.resourceVersion}")
            }

            override fun onDelete(task: TaskResource, deletedFinalStateUnknown: Boolean) {
                log.info("Task deleted: ${task.metadata.name} ${task.metadata.namespace} ${task.metadata.resourceVersion}")
                deleteTaskUseCase.delete(task.spec.name)
            }
        })

        return taskInformer
    }
}



