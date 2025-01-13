package dsl

import operator.Operator
import pipeline.Pipeline

fun pipeline(specify: Pipeline.() -> Unit): Pipeline {
    val pipeline = Pipeline()
    pipeline.specify()
    return pipeline
}

infix fun Pipeline.specify(specification: Pipeline.() -> Operator): Operator {
    val rootOperator = this.specification()
    addOperator(rootOperator)
    return rootOperator
}

infix fun Operator.pipe(next: List<Operator>): Operator {
    this.addChildren(next)
    return this // TODO: Define what has to be returned here properly.
}

infix fun Operator.pipe(next: Operator): Operator {
    this.addChild(next)
    return this
}