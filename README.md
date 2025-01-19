# Piper

![Build Gradle](https://github.com/kadiiiri/piper/actions/workflows/build-gradle.yml/badge.svg)

*Keep in mind that this project is still only in a POC state*

Piper is an early-stage tool designed for building pipelines consisting of operators that execute user-defined scripts. While still in development, the tool aims to provide a structured approach for creating and managing pipelines.

The core concept is to enable the execution of any type of operator that, under the hood, utilizes a `Kubernetes resource`. These operators can be executed in a specific sequence defined by the user.

To simplify the pipeline creation process, Piper employs a Kotlin DSL (Domain-Specific Language). This DSL makes it easy for users to define pipelines intuitively and efficiently.

An example pipeline can be found in the `examples/script.piper.kts` file. This demonstrates how **Piper** facilitates the orchestration of multiple operators in a clean and user-friendly syntax. Piper strictly detects files with the `.piper.kts` extension and supports the use of dependencies from Piper’s core library.

For instance, you can declare and include a Python script (e.g., script.py) or scripts in other languages such as Bash or KScript.

Below is a brief demonstration to serve as documentation:

Declare for instance python script called `script.py` or any other language (eg. bash, kscript,...)

```python
print('[Python] Running operator 1...')
```

Declare the path of your scripts, containing:

```kotlin
val script1 = Path.of("examples/scripts/script1.py")
val script2 = Path.of("examples/scripts/script2.sh")
val script3 = Path.of("examples/scripts/script3.sh")
val script4 = Path.of("examples/scripts/script4.sh")
val script5 = Path.of("examples/scripts/script5.sh")
val script6 = Path.of("examples/scripts/script6.kts")
```

Declare the operators you would like to run:

```kotlin
val op1 = KubernetesJobOperator("first", "python", listOf("python"), listOf("/scripts/script1.py"), script1)
val op2 = KubernetesJobOperator("second", "ubuntu:latest", listOf("/bin/sh"), listOf("/scripts/script2.sh"), script2)
val op3 = KubernetesJobOperator("third", "ubuntu:latest", listOf("/bin/sh"), listOf("/scripts/script3.sh"), script3)
val op4 = KubernetesJobOperator("fourth", "ubuntu:latest", listOf("/bin/sh"), listOf("/scripts/script4.sh"), script4)
val op5 = KubernetesJobOperator("fifth", "ubuntu:latest", listOf("/bin/sh"), listOf("/scripts/script5.sh"), script5)
val op6 = KubernetesJobOperator("sixth", "kscripting/kscript", listOf("kscript"), listOf("/scripts/script6.kts"), script6)
```

Declare the specification of your pipeline using the library of piper:

```kotlin
val pipeline = pipeline("test_pipeline") {
    op1 pipe listOf(op2 pipe op3, op4) pipe op5 pipe op6
}
```

Declare that this pipeline should be ran and visualized.

```kotlin
pipeline.visualize()
pipeline.run()
```

Now since this script has already been defined here `examples/script.piper.kts` we can just run 
`host/src/main/kotlin/Piper.kt` with an argument on the filepath `examples/script.piper.kts`.

![img.png](docs/assets/run-config.png)

Once you run it, you should be able to see that the KubernetesJobOperators you've defined are running.
You can use a tool like `kubectl` or `k9s` to view the execution of each job/pod.

