package com.github.piper.dsl

import com.github.piper.operator.Task

infix fun Task.pipe(next: Task): Task {
    return addChild(next)
}
