package com.github.piper.domain.task

data class TaskRef(
    val name: String = "",
    val dependsOn: String? = null
)