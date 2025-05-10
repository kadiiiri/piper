package com.github.piper.operator.kubernetes

data class Resources(
    var minCpuCores: Double = 4.0,
    var minMemory: Double = 1000.0,
    var maxCpuCores: Double = 4.0,
    var maxMemory: Double = 1000.0,
)