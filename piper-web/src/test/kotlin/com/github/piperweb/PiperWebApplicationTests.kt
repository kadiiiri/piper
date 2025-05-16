package com.github.piperweb

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import

@Import(com.github.piperweb.TestcontainersConfiguration::class)
@SpringBootTest
class PiperWebApplicationTests {

    @Test
    fun contextLoads() {
    }

}
