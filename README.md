# Piper

Piper is a very rough on the edges tool to create pipelines that consist of a operators that execute the desired script.

The idea is that any type of Operator that under the hood leverages a Kubernetes resource can be executed in a certain 
order that the user indicates.

To make this very simple for the end user Piper leverages a Kotlin DSL that can help creating pipelines very easily.

In the `examples/script.piper.kts` file you can find a demonstration of how Piper can deal with multiple operators in 
a nice and neat language.
Piper detects all files that end with the extension `[somefilename].piper.kts` and allows using the dependencies 
of piper's core library.

Here's a short demonstration for documentation purposes:

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
val pipeline = pipeline {
    specify {
        op1 pipe kotlin.collections.listOf(op2 pipe op3, op4) pipe op5 pipe op6
    }
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

