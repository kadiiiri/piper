package com.eska.piperweb.adapter.kubernetes

import com.github.piper.kubernetes.crd.DAGResource
import com.github.piper.kubernetes.crd.TaskResource
import io.fabric8.kubernetes.client.KubernetesClient
import io.fabric8.kubernetes.client.informers.ResourceEventHandler
import io.fabric8.kubernetes.client.informers.SharedIndexInformer
import javax.annotation.PostConstruct
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class DagInformer(private val client: KubernetesClient) {
    private val log = LoggerFactory.getLogger(DagInformer::class.java)
    private val informerFactory = client.informers()

    @PostConstruct
    fun watchDags() {
        setupWatchers()
    }

    private fun setupWatchers() {
        setupDAGInformer().run()
        setupTaskInformer().run()
    }


    fun setupDAGInformer(): SharedIndexInformer<DAGResource> {
        val dagInformer = informerFactory.sharedIndexInformerFor(
            DAGResource::class.java,
            30*1000.toLong()
        )

        dagInformer.addEventHandler(object : ResourceEventHandler<DAGResource> {
            override fun onAdd(dag: DAGResource) {
                log.info("Dag added: ${dag.metadata.name} ${dag.metadata.namespace} ${dag.metadata.resourceVersion}")
            }

            override fun onUpdate(oldDag: DAGResource, newDag: DAGResource) {
                log.info("Dag updated: ${newDag.metadata.name} ${newDag.metadata.namespace} ${newDag.metadata.resourceVersion}")
            }

            override fun onDelete(dag: DAGResource, deletedFinalStateUnknown: Boolean) {
                log.info ("Dag deleted: ${dag.metadata.name} ${dag.metadata.namespace} ${dag.metadata.resourceVersion}")
            }
        })

        return dagInformer
    }

    fun setupTaskInformer(): SharedIndexInformer<TaskResource> {

        val taskInformer = informerFactory.sharedIndexInformerFor(
            TaskResource::class.java,
            30*1000.toLong()
        )

        taskInformer.addEventHandler(object : ResourceEventHandler<TaskResource> {
            override fun onAdd(task: TaskResource) {
                log.info ("Task added: ${task.metadata.name} ${task.metadata.namespace} ${task.metadata.resourceVersion}")
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



