// TODO: Fix IDE not recognizing dependencies

import java.nio.file.Path;
import com.github.piper.dsl.pipe
import com.github.piper.dsl.pipeline
import com.github.piper.operator.kubernetes.KubernetesOperator

val script1 = Path.of("examples/scripts/script1.py")
val script2 = Path.of("examples/scripts/script2.sh")
val script3 = Path.of("examples/scripts/script3.sh")
val script4 = Path.of("examples/scripts/script4.sh")
val script5 = Path.of("examples/scripts/script5.sh")
val script6 = Path.of("examples/scripts/script6.kts")

val op1 = KubernetesOperator("first", "python", listOf("python"), listOf("/scripts/script1.py"), script1)
val op2 = KubernetesOperator("second", "ubuntu:latest", listOf("/bin/sh"), listOf("/scripts/script2.sh"), script2)
val op3 = KubernetesOperator("third", "ubuntu:latest", listOf("/bin/sh"), listOf("/scripts/script3.sh"), script3)
val op4 = KubernetesOperator("fourth", "ubuntu:latest", listOf("/bin/sh"), listOf("/scripts/script4.sh"), script4)
val op5 = KubernetesOperator("fifth", "ubuntu:latest", listOf("/bin/sh"), listOf("/scripts/script5.sh"), script5)
val op6 = KubernetesOperator("sixth", "kscripting/kscript", listOf("kscript"), listOf("/scripts/script6.kts"), script6)

val pipeline = pipeline("test_pipeline") {
    op1 pipe kotlin.collections.listOf(op2 pipe op3, op4) pipe op5 pipe op6
}

pipeline.visualize()
pipeline.activate()
