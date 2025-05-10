package pipeline

import com.github.piper.dsl.k8sParallelTask
import com.github.piper.dsl.k8sTask
import com.github.piper.dsl.pipe
import com.github.piper.dsl.pipeline
import java.time.Duration
import kotlin.test.Test

class PipelineIntegrationTest {

    @Test
    fun testSimplePipeline() {

        val pipeline = pipeline("my_pipeline") {
            timeout = Duration.ofHours(2)
            retries = 3

            k8sTask("first") {
                image = "python"
                command = listOf("python")
                scriptPath = "/Users/kadirsirimsi/dev/github/piper/piper-lib/build/resources/test/scripts/script1.py"
                resources {
                    minMemory = 512.0
                    minCpuCores = 250.0
                }
            } pipe k8sTask("fifth") {
                image = "ubuntu:latest"
                command = listOf("/bin/sh")
                scriptPath = "/Users/kadirsirimsi/dev/github/piper/piper-lib/build/resources/test/scripts/script5.sh"
            }
        }

        pipeline
            .visualize()
            .activate()

    }

    @Test
    fun testParallelPipeline() {

        val pipeline = pipeline("my_pipeline") {
            timeout = Duration.ofHours(2)
            retries = 3

            k8sTask("first") {
                image = "python"
                command = listOf("python")
                scriptPath = "/Users/kadirsirimsi/dev/github/piper/piper-lib/build/resources/test/scripts/script1.py"
                resources {
                    minMemory = 512.0
                    minCpuCores = 250.0
                }
            } pipe k8sParallelTask {

                branch {
                    k8sTask("second") {
                        image = "ubuntu:latest"
                        command = listOf("/bin/sh")
                        scriptPath = "/Users/kadirsirimsi/dev/github/piper/piper-lib/build/resources/test/scripts/script2.sh"
                        resources {
                            minMemory = 512.0
                            minCpuCores = 250.0
                        }
                    } pipe k8sTask("third") {
                        image = "ubuntu:latest"
                        command = listOf("/bin/sh")
                        scriptPath = "/Users/kadirsirimsi/dev/github/piper/piper-lib/build/resources/test/scripts/script3.sh"
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
                        scriptPath = "/Users/kadirsirimsi/dev/github/piper/piper-lib/build/resources/test/scripts/script4.sh"
                    }
                }

            } pipe k8sTask("fifth") {
                image = "ubuntu:latest"
                command = listOf("/bin/sh")
                scriptPath = "/Users/kadirsirimsi/dev/github/piper/piper-lib/build/resources/test/scripts/script5.sh"
            }
        }

        pipeline
            .visualize()
            .activate()

    }
}