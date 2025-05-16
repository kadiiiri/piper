package pipeline

import com.github.piper.dsl.Dag
import com.github.piper.dsl.k8sParallelTask
import com.github.piper.dsl.k8sTask
import com.github.piper.dsl.pipe
import java.time.Duration
import kotlin.test.Test
import org.junit.jupiter.api.Disabled

class DagIntegrationTest {

    @Test
    fun testSimplePipeline() {
        val dag = Dag("my-dag") {
            timeout = Duration.ofHours(2)
            retries = 3

            k8sTask("first") {
                image = "python"
                command = listOf("python")
                scriptPath = "src/test/resources/scripts/script1.py"
                resources {
                    minMemory = 512.0
                    minCpuCores = 250.0
                }
            } pipe k8sTask("fifth") {
                image = "ubuntu:latest"
                command = listOf("/bin/sh")
                scriptPath = "src/test/resources/scripts/script5.sh"
            }
        }

        dag
            .visualize()
            .activate()

    }

    @Test
    @Disabled
    fun testParallelPipeline() {

        val dag = Dag("my_pipeline") {
            timeout = Duration.ofHours(2)
            retries = 3

            k8sTask("first") {
                image = "python"
                command = listOf("python")
                scriptPath = "src/test/resources/scripts/script1.py"
                resources {
                    minMemory = 512.0
                    minCpuCores = 250.0
                }
            } pipe k8sParallelTask {

                branch {
                    k8sTask("second") {
                        image = "ubuntu:latest"
                        command = listOf("/bin/sh")
                        scriptPath = "src/test/resources/scripts/script2.sh"
                        resources {
                            minMemory = 512.0
                            minCpuCores = 250.0
                        }
                    } pipe k8sTask("third") {
                        image = "ubuntu:latest"
                        command = listOf("/bin/sh")
                        scriptPath = "src/test/resources/scripts/script3.sh"
                        resources {
                            minMemory = 512.0
                            minCpuCores = 250.0
                        }
                    }
                }

                branch {
                    k8sTask("fourth") {
                        image = "ubuntu:latest"
                        command = listOf("/bin/sh")
                        scriptPath = "src/test/resources/scripts/script4.sh"
                    }
                }

            } pipe k8sTask("fifth") {
                image = "ubuntu:latest"
                command = listOf("/bin/sh")
                scriptPath = "src/test/resources/scripts/script5.sh"
            }
        }

        dag
            .visualize()
            .activate()

    }
}
