package com.Ahorra.qsalud.services

import com.Ahorra.qsalud.models.pagos.*
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Service
class LinkPagoService(
    private val linkPagoWebClient: WebClient
) {
    fun generarLink(request: GenLinkRequest, token: String): Mono<String> {
        return linkPagoWebClient.post()
            .uri("/genLink")
            .headers { it.setBearerAuth(token) }
            .bodyValue(request)
            .retrieve()
            .bodyToMono(String::class.java)
    }

    fun validaBin(request: ValidaBinRequest, token: String): Mono<String> {
        return linkPagoWebClient.post().uri("/validaBin")
            .headers { it.setBearerAuth(token) }.bodyValue(request)
            .retrieve().bodyToMono(String::class.java)
    }


    fun testConexion(token: String): Mono<String> {
        return linkPagoWebClient.post().uri("/Test")
            .headers { it.setBearerAuth(token) }
            .retrieve().bodyToMono(String::class.java)
    }

    fun searchReceipts(request: PolizaRequest, token: String): Mono<String> {
        return linkPagoWebClient.post().uri("/searchReceipts")
            .headers { it.setBearerAuth(token) }.bodyValue(request)
            .retrieve().bodyToMono(String::class.java)
    }

    fun searchLink(request: PolizaRequest, token: String): Mono<String> {
        return linkPagoWebClient.post().uri("/searchlink")
            .headers { it.setBearerAuth(token) }.bodyValue(request)
            .retrieve().bodyToMono(String::class.java)
    }

    fun cancelLink(request: CancelLinkRequest, token: String): Mono<String> {
        return linkPagoWebClient.post().uri("/cancelLink")
            .headers { it.setBearerAuth(token) }.bodyValue(request)
            .retrieve().bodyToMono(String::class.java)
    }

    fun consultarDomiciliacion(request: ConsulDomRequest, token: String): Mono<String> {
        return linkPagoWebClient.post()
            .uri("/consulDom")
            .headers { it.setBearerAuth(token) }
            .bodyValue(request)
            .retrieve()
            .bodyToMono(String::class.java)
    }

    fun genWebPay(request: GenWebPayRequest, token: String): Mono<String> {
        return linkPagoWebClient.post().uri("/genWebPay")
            .headers { it.setBearerAuth(token) }.bodyValue(request)
            .retrieve().bodyToMono(String::class.java)
    }

    fun fareceipt(request: PolizaRequest, token: String): Mono<String> {
        return linkPagoWebClient.post().uri("/fareceipt")
            .headers { it.setBearerAuth(token) }.bodyValue(request)
            .retrieve().bodyToMono(String::class.java)
    }
}