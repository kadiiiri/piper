package com.github.piperweb.application.port

import com.github.piperweb.adapter.kubernetes.executor.job.ExecutorRequest

interface K8sJobExecutorPort {
    fun execute(executorRequest: ExecutorRequest)
}