package com.github.piper.dsl

import com.github.piper.domain.dag.Dag

fun Dag(id: String, block: Dag.() -> Unit): Dag {
    return Dag(id).apply(block)
}
