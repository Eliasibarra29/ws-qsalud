package com.Ahorra.qsalud.services

import com.Ahorra.qsalud.models.brokers.*
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import java.util.Base64

@Service
class DocumentosService(private val siseWebClient: WebClient) {

    fun descargarFormato(request: FormatPrintingRequest, token: String): Mono<FormatPrintingResponse> {
        return siseWebClient.post()
            .uri("/api/printing/format-printing")
            .headers { it.setBearerAuth(token) }
            .bodyValue(request)
            .retrieve()
            .bodyToMono(FormatPrintingResponse::class.java)
    }

    fun descargarSolicitudFirmada(solicitud: String, token: String): Mono<ByteArray> {
        val request = mapOf("solicitud" to solicitud)
        return siseWebClient.post()
            .uri("/api/brokers/get-siggned-application")
            .headers { it.setBearerAuth(token) }
            .bodyValue(request)
            .retrieve()
            .bodyToMono(FormatPrintingResponse::class.java)
            .map { response -> Base64.getDecoder().decode(response.base64 ?: "") }
    }
}