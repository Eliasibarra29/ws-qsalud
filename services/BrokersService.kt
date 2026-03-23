package com.Ahorra.qsalud.services

import com.Ahorra.qsalud.models.brokers.MovimientosRequest
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Service
class BrokersService(
    private val siseWebClient: WebClient
) {

    fun crearMovimiento(request: MovimientosRequest, accessToken: String): Mono<String> {
        return siseWebClient.post()
            .uri("/api/brokers/create")
            .headers { it.setBearerAuth(accessToken) }
            .bodyValue(request)
            .retrieve()
            .bodyToMono(String::class.java)
    }
}