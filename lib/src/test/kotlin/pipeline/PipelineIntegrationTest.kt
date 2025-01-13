package pipeline

import dsl.pipe
import dsl.pipeline
import dsl.specify
import operator.kubernetes.KubernetesJobOperator
import org.junit.jupiter.api.Disabled
import kotlin.io.path.toPath
import kotlin.test.Test

class PipelineIntegrationTest {

    @Test
    @Disabled("Only run this integration test to try out the application.")
    fun test() {
        val script1 = javaClass.getResource("/scripts/script1.py")!!.toURI().toPath()
        val script2 = javaClass.getResource("/scripts/script2.sh")!!.toURI().toPath()
        val script3 = javaClass.getResource("/scripts/script3.sh")!!.toURI().toPath()
        val script4 = javaClass.getResource("/scripts/script4.sh")!!.toURI().toPath()
        val script5 = javaClass.getResource("/scripts/script5.sh")!!.toURI().toPath()
        val script6 = javaClass.getResource("/scripts/script6.kts")!!.toURI().toPath()

        val op1= KubernetesJobOperator("first", "python", listOf("python"), listOf("/scripts/script1.py"), script1)
        val op2= KubernetesJobOperator("second", "ubuntu:latest", listOf("/bin/sh"), listOf("/scripts/script2.sh"), script2)
        val op3 = KubernetesJobOperator("third", "ubuntu:latest", listOf("/bin/sh"), listOf("/scripts/script3.sh"), script3)
        val op4 = KubernetesJobOperator("fourth", "ubuntu:latest", listOf("/bin/sh"), listOf("/scripts/script4.sh"), script4)
        val op5 = KubernetesJobOperator("fifth", "ubuntu:latest", listOf("/bin/sh"), listOf("/scripts/script5.sh"), script5)
        val op6 = KubernetesJobOperator("sixth", "kscripting/kscript", listOf("kscript"), listOf("/scripts/script6.kts"), script6)

        val pipeline = pipeline {
            specify {
                op1 pipe listOf(op2 pipe op3, op4) pipe op5 pipe op6
            }
        }

        pipeline.visualize()
        pipeline.run()
    }
}
