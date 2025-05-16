package com.github.piper.dsl

import com.github.piper.domain.task.Task

infix fun Task.pipe(next: Task): Task {
    return addChild(next)
}
