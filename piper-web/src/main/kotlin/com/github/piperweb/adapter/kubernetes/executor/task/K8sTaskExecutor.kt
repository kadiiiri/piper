package com.github.piperweb.adapter.kubernetes.executor.task

import com.github.piper.primitives.kubernetes.K8sResourceStatus.FINISHED
import com.github.piperweb.adapter.kubernetes.executor.job.ExecutorRequest
import com.github.piperweb.adapter.kubernetes.executor.job.K8sJobExecutor
import com.github.piperweb.domain.model.Task
import com.github.piperweb.domain.usecase.FindTaskByNameUseCase
import com.github.piperweb.port.K8sTaskExecutorPort
import org.springframework.stereotype.Service

@Service
class K8sTaskExecutor(
    val k8sJobExecutor: K8sJobExecutor,
    val findTaskByNameUseCase: FindTaskByNameUseCase
) : K8sTaskExecutorPort {

    override fun execute(task: Task)  {
        if (task.dependsOn != null && parentTaskNotFinished(task)) return
        val executorRequest = task.toExecutorRequest()
        return k8sJobExecutor.execute(executorRequest)
    }

    fun parentTaskNotFinished(task: Task): Boolean {
        return findTaskByNameUseCase.findByName(task.dependsOn!!.name)?.status != FINISHED
    }
}

private fun Task.toExecutorRequest() = ExecutorRequest(
    name = "$name-$id",
    image = image,
    command = command,
    script = script,
    scriptPath = scriptPath,
    minCpuCores = resources.minCpuCores,
    minMemory = resources.minMemory,
    maxCpuCores = resources.maxCpuCores,
    maxMemory = resources.maxMemory,
)