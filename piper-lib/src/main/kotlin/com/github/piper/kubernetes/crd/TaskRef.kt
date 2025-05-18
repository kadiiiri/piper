package com.github.piper.kubernetes.crd

data class TaskRef(
    val name: String = "",
    val dependsOn: String? = null
)