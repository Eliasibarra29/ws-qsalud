package com.Ahorra.qsalud.services

import com.Ahorra.qsalud.models.brokers.*
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Service
class DocumentosService(
    private val siseWebClient: WebClient
) {
    // Generar formato de impresión (Cotización, Emisión o Solicitud)
    fun descargarFormato(request: FormatPrintingRequest, token: String): Mono<FormatPrintingResponse> {
        return siseWebClient.post()
            .uri("/api/printing/format-printing")
            .headers { it.setBearerAuth(token) }
            .bodyValue(request)
            .retrieve()
            .bodyToMono(FormatPrintingResponse::class.java)
    }

    // Solicitar OTP
    fun solicitarToken(request: TokenRequest, token: String): Mono<String> {
        return siseWebClient.post()
            .uri("/api/brokers/application_getToken")
            .headers { it.setBearerAuth(token) }
            .bodyValue(request)
            .retrieve()
            .bodyToMono(String::class.java)
    }

    fun descargarSolicitudFirmada(solicitud: String, token: String): Mono<String> {
        val request = mapOf("solicitud" to solicitud)
        return siseWebClient.post()
            .uri("/api/brokers/get-siggned-application")
            .headers { it.setBearerAuth(token) }
            .bodyValue(request)
            .retrieve()
            .bodyToMono(String::class.java)
    }

    // Verificar CFDI
    fun verificarCfdi(request: CfdiRequest, token: String): Mono<String> {
        return siseWebClient.post()
            .uri("/api/brokers/verify-cfdi")
            .headers { it.setBearerAuth(token) }
            .bodyValue(request)
            .retrieve()
            .bodyToMono(String::class.java)
    }

    // Validar OTP
    fun validarToken(request: VerifyTokenRequest, token: String): Mono<String> {
        return siseWebClient.post()
            .uri("/api/brokers/application_verifyToken")
            .headers { it.setBearerAuth(token) }
            .bodyValue(request)
            .retrieve()
            .bodyToMono(String::class.java)
    }

    // Guardar firma biométrica
    fun guardarFirma(request: SignatureRequest, token: String): Mono<String> {
        return siseWebClient.post()
            .uri("/api/brokers/signature-data")
            .headers { it.setBearerAuth(token) }
            .bodyValue(request)
            .retrieve()
            .bodyToMono(String::class.java)
    }
}