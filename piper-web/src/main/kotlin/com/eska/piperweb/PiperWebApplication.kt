package com.eska.piperweb

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PiperWebApplication

fun main(args: Array<String>) {
    runApplication<PiperWebApplication>(*args)
}
