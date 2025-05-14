package com.github.piper.kubernetes.crd

import io.fabric8.kubernetes.client.KubernetesClientBuilder
import java.nio.file.Files
import java.nio.file.Paths

fun register() {
    registerDAGResourceDefinition()
    registerTaskResourceDefinition()
}

private fun registerDAGResourceDefinition() {
    val client = KubernetesClientBuilder().build()
    val crdYamlPath = Paths.get("build/resources/main/dagresources.piper.eska.com-v1.yml")

    if (Files.exists(crdYamlPath)) {
        val crdYaml = Files.readString(crdYamlPath)
        client.load(crdYaml.byteInputStream()).create()
        println("DAGResource CRD registered successfully!")
    } else {
        println("CRD file not found: $crdYamlPath")
    }
}

private fun registerTaskResourceDefinition() {
    val client = KubernetesClientBuilder().build()
    val crdYamlPath = Paths.get("build/resources/main/taskresources.piper.eska.com-v1.yml")

    if (Files.exists(crdYamlPath)) {
        val crdYaml = Files.readString(crdYamlPath)
        client.load(crdYaml.byteInputStream()).create()
        println("DAGResource CRD registered successfully!")
    } else {
        println("CRD file not found: $crdYamlPath")
    }
}

