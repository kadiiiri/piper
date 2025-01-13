import java.nio.file.Path

// TODO: Fix IDE not recognizing dependencies

import dsl.pipe
import dsl.pipeline
import dsl.specify
import operator.kubernetes.KubernetesJobOperator
import java.nio.file.Path.of

val script1 = of("examples/scripts/script1.py")
val script2 = of("examples/scripts/script2.sh")
val script3 = of("examples/scripts/script3.sh")
val script4 = of("examples/scripts/script4.sh")
val script5 = of("examples/scripts/script5.sh")
val script6 = of("examples/scripts/script6.kts")

val op1 = KubernetesJobOperator("first", "python", listOf("python"), listOf("/scripts/script1.py"), script1)
val op2 = KubernetesJobOperator("second", "ubuntu:latest", listOf("/bin/sh"), listOf("/scripts/script2.sh"), script2)
val op3 = KubernetesJobOperator("third", "ubuntu:latest", listOf("/bin/sh"), listOf("/scripts/script3.sh"), script3)
val op4 = KubernetesJobOperator("fourth", "ubuntu:latest", listOf("/bin/sh"), listOf("/scripts/script4.sh"), script4)
val op5 = KubernetesJobOperator("fifth", "ubuntu:latest", listOf("/bin/sh"), listOf("/scripts/script5.sh"), script5)
val op6 = KubernetesJobOperator("sixth", "kscripting/kscript", listOf("kscript"), listOf("/scripts/script6.kts"), script6)

val pipeline = pipeline {
    specify {
        op1 pipe kotlin.collections.listOf(op2 pipe op3, op4) pipe op5 pipe op6
    }
}

pipeline.visualize()
pipeline.run()


