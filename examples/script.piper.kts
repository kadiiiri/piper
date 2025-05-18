// TODO: Fix IDE not recognizing dependencies

import com.github.piper.dsl.Dag
import com.github.piper.dsl.k8sParallelTask
import com.github.piper.dsl.k8sTask
import com.github.piper.dsl.pipe
//import com.github.piper.kubernetes.crd.register
import com.github.piper.primitives.time.Schedule.OneOffSchedule
import java.time.OffsetDateTime


// register() // should only run for the first time to register the CRDs

val dag = Dag("my_pipeline") {

    k8sTask("first") {
        image = "python"
        command = listOf("python")
        scriptPath = "src/test/resources/scripts/script1.py"
        resources {
            minMemory = 512.0
            maxMemory = 1024.0
            minCpuCores = 1.0
            maxCpuCores = 2.0
        }
    } pipe k8sParallelTask {

        branch {
            k8sTask("second") {
                image = "ubuntu:latest"
                command = listOf("/bin/sh")
                scriptPath = "src/test/resources/scripts/script2.sh"
                resources {
                    minMemory = 512.0
                    maxMemory = 1024.0
                    minCpuCores = 1.0
                    maxCpuCores = 2.0
                }
            } pipe k8sTask("third") {
                image = "ubuntu:latest"
                command = listOf("/bin/sh")
                scriptPath = "src/test/resources/scripts/script3.sh"
                resources {
                    minMemory = 512.0
                    maxMemory = 1024.0
                    minCpuCores = 1.0
                    maxCpuCores = 2.0
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
