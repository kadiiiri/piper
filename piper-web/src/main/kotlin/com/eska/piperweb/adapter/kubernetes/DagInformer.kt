package com.eska.piperweb.adapter.kubernetes

import com.eska.piperweb.adapter.database.entities.DagRepository
import com.eska.piperweb.domain.model.DAG
import com.eska.piperweb.domain.model.ResourceStatus
import com.eska.piperweb.domain.model.toEntity
import com.github.piper.kubernetes.crd.DAGResource
import io.fabric8.kubernetes.client.KubernetesClient
import io.fabric8.kubernetes.client.informers.ResourceEventHandler
import io.fabric8.kubernetes.client.informers.SharedIndexInformer
import java.time.LocalDateTime
import java.util.*
import javax.annotation.PostConstruct
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class DagInformer(
    private val client: KubernetesClient,
    private val dagRepository: DagRepository
) {
    private val log = LoggerFactory.getLogger(DagInformer::class.java)
    private val informerFactory = client.informers()


    @PostConstruct
    fun setup() {
        setupDAGInformer().run()
    }

    fun setupDAGInformer(): SharedIndexInformer<DAGResource> {
        val dagInformer = informerFactory.sharedIndexInformerFor(
            DAGResource::class.java,
            30*1000.toLong()
        )

        dagInformer.addEventHandler(object : ResourceEventHandler<DAGResource> {
            override fun onAdd(dagResource: DAGResource) {
                log.info("Dag added: ${dagResource.metadata.name} ${dagResource.metadata.namespace} ${dagResource.metadata.resourceVersion}")
                val dag = DAG(
                    id = UUID.randomUUID(),
                    name = dagResource.spec.name,
                    createdAt = dagResource.metadata.creationTimestamp.toString(),
                    status = ResourceStatus.fromString(dagResource.status.status),
                    scheduledFor = LocalDateTime.now().plusMinutes(30)
                )

                dagRepository.save(dag.toEntity())
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
}



