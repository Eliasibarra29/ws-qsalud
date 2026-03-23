package com.Ahorra.qsalud.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class WebClientConfig {

    @Bean
    fun linkPagoWebClient(builder: WebClient.Builder): WebClient {
        return builder
            .baseUrl("https://pagofacturasqa.qualitassalud.com.mx/apilink")
            .build()
    }

    @Bean
    fun siseWebClient(builder: WebClient.Builder): WebClient {
        return builder
            .baseUrl("https://webservicesqa.qualitassalud.com.mx")
            .build()
    }
}