package com.github.piper.dsl

import com.github.piper.pipeline.Pipeline

fun pipeline(id: String, block: Pipeline.() -> Unit): Pipeline {
    return Pipeline(id).apply(block)
}
