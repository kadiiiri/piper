package com.github.piper.operator.kubernetes

import com.github.piper.kubernetes.adapter.KubeJobAdapter
import com.github.piper.kubernetes.run.RunConfig
import com.github.piper.operator.Operator
import com.github.piper.util.appendUID
import java.nio.file.Path

abstract class KubernetesOperator(
    private val id: String,
    private val image: String,
    private val command: List<String>,
    private val args: List<String>,
    private val script: Path,
): Operator(id) {
    val resourceName = id.appendUID()
}

class KubernetesJobOperator(
    private val id: String,
    private val image: String,
    private val command: List<String>,
    private val args: List<String>,
    private val script: Path
) : KubernetesOperator(id, image, command, args, script) {
    private val kubeAdapter = KubeJobAdapter()

    override fun execute() {
        log.info { "Starting execution of operator '$id' with image '$image' and script '$script'" }

        val runConfig = RunConfig(resourceName, image, command, args, emptyMap(), script)

        kubeAdapter.run(runConfig)
        kubeAdapter.awaitCompletion(resourceName) // TODO: Employ various strategies (eg. parallelism, no awaiting,..)
        children.forEach { it.execute() }
    }
}

