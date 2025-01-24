package com.github.piper.kubernetes.run

import java.nio.file.Path

data class RunConfig(
    val resourceName: String,
    val image: String,
    val command: List<String>,
    val args: List<String>,
    val env: Map<String, String>,
    val script: Path
)