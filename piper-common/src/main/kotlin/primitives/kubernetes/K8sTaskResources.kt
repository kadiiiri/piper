package com.github.piper.primitives.kubernetes

data class K8sTaskResources(
    var minCpuCores: Double = 1.0,
    var minMemory: Double = 512.0,
    var maxCpuCores: Double = 1.0,
    var maxMemory: Double = 512.0,
)