package com.github.piperweb

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class PiperWebApplication

fun main(args: Array<String>) {
    runApplication<PiperWebApplication>(*args)
}
