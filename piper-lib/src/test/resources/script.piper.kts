// This is merely here because for now it is easier to construct this script with help of the IDE
// TODO: Make it so that the examples/script.piper.kts file also has proper dependency detection.
import dsl.pipe
import dsl.pipeline
import operator.kubernetes.KubernetesJobOperator
import java.nio.file.Path

val script1 = Path.of("./scripts/script1.py")
val script2 = Path.of("./scripts/script2.sh")
val script3 = Path.of("./scripts/script3.sh")
val script4 = Path.of("./scripts/script4.sh")
val script5 = Path.of("./scripts/script5.sh")
val script6 = Path.of("./scripts/script6.kts")


val op1 = KubernetesJobOperator("first", "python", listOf("python"), listOf("/scripts/script1.py"), script1)
val op2 = KubernetesJobOperator("second", "ubuntu:latest", listOf("/bin/sh"), listOf("/scripts/script2.sh"), script2)
val op3 = KubernetesJobOperator("third", "ubuntu:latest", listOf("/bin/sh"), listOf("/scripts/script3.sh"), script3)
val op4 = KubernetesJobOperator("fourth", "ubuntu:latest", listOf("/bin/sh"), listOf("/scripts/script4.sh"), script4)
val op5 = KubernetesJobOperator("fifth", "ubuntu:latest", listOf("/bin/sh"), listOf("/scripts/script5.sh"), script5)
val op6 = KubernetesJobOperator("sixth", "kscripting/kscript", listOf("kscript"), listOf("/scripts/script6.kts"), script6)

val pipeline = pipeline("test_pipeline") {
    op1 pipe listOf(op2 pipe op3, op4) pipe op5 pipe op6
}

pipeline.visualize()
pipeline.run()