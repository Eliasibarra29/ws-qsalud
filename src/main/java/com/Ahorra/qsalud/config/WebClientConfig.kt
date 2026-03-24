package com.Ahorra.qsalud.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class WebClientConfig(
    @Value("\${qualitas.api.link-pago.base-url}") private val linkPagoBaseUrl: String,
    @Value("\${qualitas.api.sise.base-url}") private val siseBaseUrl: String
) {

    @Bean
    fun linkPagoWebClient(builder: WebClient.Builder): WebClient {
        return builder
            .baseUrl(linkPagoBaseUrl)
            .build()
    }

    @Bean
    fun siseWebClient(builder: WebClient.Builder): WebClient {
        return builder
            .baseUrl(siseBaseUrl)
            .build()
    }
}