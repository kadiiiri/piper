package operator.kubernetes

import adapter.kubernetes.KubeAdapter
import adapter.kubernetes.KubeJobAdapter
import adapter.kubernetes.KubePodAdapter
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


class KubernetesPodOperator(
    private val name: String,
    private val image: String,
    private val command: List<String>,
    private val args: List<String>,
    private val script: Path,
) : KubernetesOperator(name, image, command, args, script) {
    private val kubeAdapter: KubeAdapter = KubePodAdapter()

    override fun execute() {
        log.info { "Starting execution of operator '$name' with image '$image' and script '$script'" }
        kubeAdapter.run(resourceName, image, command, args, script)
        kubeAdapter.awaitCompletion(resourceName)
        children.forEach { it.execute() }
    }
}

class KubernetesJobOperator(
    private val name: String,
    private val image: String,
    private val command: List<String>,
    private val args: List<String>,
    private val script: Path
) : KubernetesOperator(name, image, command, args, script) {
    private val kubeAdapter: KubeAdapter = KubeJobAdapter()

    override fun execute() {
        log.info { "Starting execution of operator '$name' with image '$image' and script '$script'" }
        kubeAdapter.run(resourceName, image, command, args, script)
        kubeAdapter.awaitCompletion(resourceName)
        children.forEach { it.execute() }
    }
}

