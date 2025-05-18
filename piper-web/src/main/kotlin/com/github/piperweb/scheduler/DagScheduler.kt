package com.github.piperweb.scheduler

import com.github.piper.primitives.kubernetes.K8sResourceStatus.AWAITING_EXECUTION
import com.github.piperweb.domain.usecase.FindAllDagsUseCase
import com.github.piperweb.domain.usecase.FindTasksByDagUseCase
import com.github.piperweb.port.K8sTaskExecutorPort
import com.github.piperweb.scheduler.data.DagSchedulingContext
import com.github.piperweb.scheduler.data.toContext
import java.time.OffsetDateTime
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class DagScheduler(
    val findAllDagsUseCase: FindAllDagsUseCase,
    val findTasksByDagUseCase: FindTasksByDagUseCase,
    val k8sTaskExecutor: K8sTaskExecutorPort
) {
    val log = LoggerFactory.getLogger(DagScheduler::class.java)

    @Scheduled(cron = "*/15 * * * * *")
    fun schedule() {

        log.info("Start scheduling dags")

        val dags = findAllDagsUseCase.findAll()

        val contexts = dags.map { dag ->
            dag.toContext(findTasksByDagUseCase.findByDagId(dag.id))
        }

        contexts
            .filter { it.status == AWAITING_EXECUTION }
            .filter { !it.schedule.nextExecutionTime()!!.isAfter(OffsetDateTime.now()) }
            .forEach { execute(it) }
    }

    private fun execute(context: DagSchedulingContext) {
        context.tasks.forEach { task ->
            k8sTaskExecutor.execute(task)
        }
    }
}