package com.github.piperweb.adapter.kubernetes.informer

import com.github.piper.kubernetes.crd.DAGResource
import com.github.piperweb.domain.model.Dag
import com.github.piperweb.application.usecase.CreateDagUseCase
import com.github.piperweb.application.usecase.DeleteDagUseCase
import com.github.piperweb.application.usecase.UpdateDagUseCase
import io.fabric8.kubernetes.client.KubernetesClient
import io.fabric8.kubernetes.client.informers.ResourceEventHandler
import io.fabric8.kubernetes.client.informers.SharedIndexInformer
import java.time.OffsetDateTime
import java.util.*
import javax.annotation.PostConstruct
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class DagInformer(
    private val client: KubernetesClient,
    private val createDagUseCase: CreateDagUseCase,
    private val updateDagUseCase: UpdateDagUseCase,
    private val deleteDagUseCase: DeleteDagUseCase,
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
                val dag = Dag(
                    id = UUID.randomUUID(),
                    name = dagResource.spec.name,
                    createdAt = OffsetDateTime.parse(dagResource.metadata.creationTimestamp).toLocalDateTime(),
                    status = dagResource.status!!.status,
                    schedule = dagResource.spec.schedule!!
                )
                createDagUseCase.create(dag)

                log.info("Dag added: $dag")
            }

            override fun onUpdate(oldDag: DAGResource, newDag: DAGResource) {
                log.info("Dag updated: oldDag: '$oldDag' newDag: '$newDag'")
                val dag = Dag(
                    id = UUID.randomUUID(),
                    name = newDag.spec.name,
                    createdAt = OffsetDateTime.parse(newDag.metadata.creationTimestamp).toLocalDateTime(),
                    status = newDag.status!!.status,
                    schedule = newDag.spec.schedule!!
                )
                updateDagUseCase.update(dag)
            }

            override fun onDelete(dag: DAGResource, deletedFinalStateUnknown: Boolean) {
                deleteDagUseCase.delete(dag.spec.name)
                log.info ("Dag deleted: $dag")
            }
        })

        return dagInformer
    }
}



