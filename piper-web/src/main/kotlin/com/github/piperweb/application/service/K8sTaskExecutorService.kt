package com.github.piperweb.application.service

import com.github.piperweb.application.port.K8sJobExecutorPort
import com.github.piperweb.application.port.K8sTaskExecutorPort
import com.github.piperweb.domain.mapper.TaskMapper.toExecutorRequest
import com.github.piperweb.domain.model.Task
import org.springframework.stereotype.Service

@Service
class K8sTaskExecutor(
    val k8sJobExecutorPort: K8sJobExecutorPort
) : K8sTaskExecutorPort {

    override fun execute(task: Task)  {
        if (!task.canExecute()) return
        val executorRequest = task.toExecutorRequest()
        return k8sJobExecutorPort.execute(executorRequest)
    }
}
