package com.github.piperweb.config

import io.fabric8.kubernetes.client.KubernetesClient
import io.fabric8.kubernetes.client.KubernetesClientBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class KubernetesClientProvider {

    @Bean
    fun kubernetesClient(): KubernetesClient {
        return KubernetesClientBuilder().build()
    }
}