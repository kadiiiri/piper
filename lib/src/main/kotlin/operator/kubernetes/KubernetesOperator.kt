package operator.kubernetes

import adapter.kubernetes.KubeJobAdapter
import adapter.kubernetes.run.RunConfig
import operator.Operator
import util.appendUID
import java.nio.file.Path

abstract class KubernetesOperator(
    private val name: String,
    private val image: String,
    private val command: List<String>,
    private val args: List<String>,
    private val script: Path,
): Operator(name) {
    val resourceName = name.appendUID()
}

class KubernetesJobOperator(
    private val name: String,
    private val image: String,
    private val command: List<String>,
    private val args: List<String>,
    private val script: Path
) : KubernetesOperator(name, image, command, args, script) {
    private val kubeAdapter = KubeJobAdapter()

    override fun execute() {
        log.info { "Starting execution of operator '$name' with image '$image' and script '$script'" }

        val runConfig = RunConfig(resourceName, image, command, args, emptyMap(), script)

        kubeAdapter.run(runConfig)
        kubeAdapter.awaitCompletion(resourceName)
        children.forEach { it.execute() }
    }
}

