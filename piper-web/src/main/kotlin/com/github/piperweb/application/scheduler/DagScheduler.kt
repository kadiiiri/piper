package com.github.piperweb.application.scheduler

import com.github.piperweb.application.dto.request.DagSchedulingContext
import com.github.piperweb.application.dto.request.toContext
import com.github.piperweb.application.port.K8sTaskExecutorPort
import com.github.piperweb.application.usecase.FindAllDagsUseCase
import com.github.piperweb.application.usecase.FindTasksByDagUseCase
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class DagScheduler(
    val findAllDagsUseCase: FindAllDagsUseCase,
    val findTasksByDagUseCase: FindTasksByDagUseCase,
    val k8sTaskExecutor: K8sTaskExecutorPort
) {
    val log: Logger = LoggerFactory.getLogger(DagScheduler::class.java)

    @Scheduled(cron = "*/15 * * * * *")
    fun schedule() {

        log.info("Start scheduling dags")

        val dags = findAllDagsUseCase.findAll()
            .filter { it.canBeScheduled() }

        val contexts = dags.map { dag ->
            dag.toContext(findTasksByDagUseCase.findByDagId(dag.id))
        }

        contexts
            .forEach { it ->
                log.info("Scheduling Dag ${it.name} that was scheduled for '${it.schedule.nextExecutionTime()}' " +
                        "with '${it.tasks.count()}' tasks.")

                execute(it)
            }
    }

    private fun execute(context: DagSchedulingContext) {
        context.tasks.forEach { task ->
            k8sTaskExecutor.execute(task)
        }
    }
}