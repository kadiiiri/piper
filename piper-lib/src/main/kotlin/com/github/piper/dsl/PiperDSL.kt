package com.github.piper.dsl

import com.github.piper.operator.Operator
import com.github.piper.pipeline.Pipeline

fun pipeline(id: String, specify: Pipeline.() -> Operator): Pipeline {
    val pipeline = Pipeline(id)
    val rootOperator = pipeline.specify()
    pipeline.addOperator(rootOperator)
    return pipeline
}

infix fun Operator.pipe(next: List<Operator>): Operator {
    this.addChildren(next)
    return this // TODO: Define what has to be returned here properly.
}

infix fun Operator.pipe(next: Operator): Operator {
    this.addChild(next)
    return this
}