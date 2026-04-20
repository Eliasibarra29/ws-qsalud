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

    fun solicitarToken(request: TokenRequest, token: String): Mono<String> {
        return siseWebClient.post()
            .uri("/api/brokers/application_getToken")
            .headers { it.setBearerAuth(token) }
            .bodyValue(request)
            .retrieve()
            .bodyToMono(String::class.java)
    }

    fun validarToken(request: VerifyTokenRequest, token: String): Mono<String> {
        return siseWebClient.post()
            .uri("/api/brokers/application_verifyToken")
            .headers { it.setBearerAuth(token) }
            .bodyValue(request)
            .retrieve()
            .bodyToMono(String::class.java)
    }

    fun guardarFirma(request: SignatureRequest, token: String): Mono<String> {
        return siseWebClient.post()
            .uri("/api/brokers/signature-data")
            .headers { it.setBearerAuth(token) }
            .bodyValue(request)
            .retrieve()
            .bodyToMono(String::class.java)
    }

    fun verificarCfdi(request: CfdiRequest, token: String): Mono<String> {
        return siseWebClient.post()
            .uri("/api/brokers/verify-cfdi")
            .headers { it.setBearerAuth(token) }
            .bodyValue(request)
            .retrieve()
            .bodyToMono(String::class.java)
    }
}