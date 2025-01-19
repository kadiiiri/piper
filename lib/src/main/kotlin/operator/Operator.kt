package operator

import io.github.oshai.kotlinlogging.KLogger
import io.github.oshai.kotlinlogging.KotlinLogging.logger

abstract class Operator(private val id: String) {
    abstract fun execute()

    protected val log: KLogger = logger {}
    protected val children: MutableList<Operator> = mutableListOf()

    fun addChildren(operators: List<Operator>) {
        children.addAll(operators)
    }

    fun addChild(operator: Operator) {
        children.add(operator)
    }

    fun logTree(indent: String = "") {
        log.info { "$indent$id" }
        children.forEach { it.logTree("$indent  ") }
    }

    override fun toString(): String {
        return id
    }
}