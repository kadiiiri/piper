package com.github.piper.operator.kubernetes

import com.github.piper.kubernetes.executors.ExecutorRequest
import com.github.piper.kubernetes.executors.K8sExecutor
import com.github.piper.operator.Operator
import com.github.piper.util.appendUID
import java.nio.file.Path

class KubernetesOperator(
    private val id: String,
    private val image: String,
    private val command: List<String>,
    private val args: List<String>,
    private val script: Path,
): Operator(id) {
    val name = id.appendUID()

    override fun execute() {
        log.info { "Starting execution of operator '$id' with image '$image' and script '$script'" }

        // TODO: Delegate these constants to user input, with some default constants defined somewhere else.

        val executorRequest = ExecutorRequest(
            name = name,
            image = image,
            command = command,
            args = args,
            script = script,
            minCpuCores = 4.0,
            minMemory = 1000.0,
            maxCpuCores = 4.0,
            maxMemory = 1000.0,
        )

        val k8sExecutor = K8sExecutor(executorRequest)

        k8sExecutor.execute()

        children.forEach { it.execute() }
    }
}