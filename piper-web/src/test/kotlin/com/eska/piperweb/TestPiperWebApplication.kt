package com.eska.piperweb

import org.springframework.boot.fromApplication
import org.springframework.boot.with


fun main(args: Array<String>) {
    fromApplication<PiperWebApplication>().with(TestcontainersConfiguration::class).run(*args)
}
