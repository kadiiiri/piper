package pipeline

import operator.Operator

class Pipeline {
    private var rootOperator: Operator? = null

    fun addOperator(operator: Operator) {
        if (rootOperator == null) {
            rootOperator = operator
        } else {
            rootOperator!!.addChild(operator)
        }
    }

    fun run() {
        requireNotNull(rootOperator) { "No operators defined for this pipeline." }
        rootOperator!!.execute()
    }

    fun visualize() {
        rootOperator?.logTree()
    }
}