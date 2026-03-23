package com.Ahorra.qsalud.services

import com.Ahorra.qsalud.models.brokers.FormatPrintingRequest
import com.Ahorra.qsalud.models.brokers.FormatPrintingResponse
import com.Ahorra.qsalud.models.brokers.SignatureRequest
import com.Ahorra.qsalud.models.brokers.TokenRequest
import com.Ahorra.qsalud.models.brokers.VerifyTokenRequest
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Service
class DocumentosService(
    private val siseWebClient: WebClient
) {
    // Generar formato de impresión (Cotización, Emisión o Solicitud) [cite: 748, 781, 812]
    fun descargarFormato(request: FormatPrintingRequest, token: String): Mono<FormatPrintingResponse> {
        return siseWebClient.post()
            .uri("/api/printing/format-printing") [cite: 748, 781, 812]
        .headers { it.setBearerAuth(token) } [cite: 750, 783, 816]
        .bodyValue(request)
            .retrieve()
            .bodyToMono(FormatPrintingResponse::class.java)
    }

    // Solicitar OTP [cite: 1016]
    fun solicitarToken(request: TokenRequest, token: String): Mono<String> {
        return siseWebClient.post()
            .uri("/api/brokers/application_getToken") [cite: 1016]
        .headers { it.setBearerAuth(token) }
            .bodyValue(request)
            .retrieve()
            .bodyToMono(String::class.java)
    }

    // Validar OTP [cite: 1055]
    fun validarToken(request: VerifyTokenRequest, token: String): Mono<String> {
        return siseWebClient.post()
            .uri("/api/brokers/application_verifyToken") [cite: 1055]
        .headers { it.setBearerAuth(token) }
            .bodyValue(request)
            .retrieve()
            .bodyToMono(String::class.java)
    }

    // Guardar firma biométrica [cite: 870]
    fun guardarFirma(request: SignatureRequest, token: String): Mono<String> {
        return siseWebClient.post()
            .uri("/api/brokers/signature-data") [cite: 870]
        .headers { it.setBearerAuth(token) }
            .bodyValue(request)
            .retrieve()
            .bodyToMono(String::class.java)
    }
}