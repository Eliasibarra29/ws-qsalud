package com.Ahorra.qsalud.services

import com.Ahorra.qsalud.models.auth.LinkPagoAuthRequest
import com.Ahorra.qsalud.models.auth.LinkPagoAuthResponse
import com.Ahorra.qsalud.models.auth.SiseAuthRequest
import com.Ahorra.qsalud.models.auth.SiseAuthResponse
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Service
class QualitasAuthService(
    private val linkPagoWebClient: WebClient,
    private val siseWebClient: WebClient
) {

    fun getLinkPagoToken(username: String, password: String): Mono<String> {
        val request = LinkPagoAuthRequest(username, password)

        return linkPagoWebClient.post()
            .uri("/login")
            .bodyValue(request)
            .retrieve()
            .bodyToMono(LinkPagoAuthResponse::class.java)
            .map { it.token ?: "" }
    }

    fun getSiseAccessToken(user: String, password: String): Mono<String> {
        val request = SiseAuthRequest(user, password)

        return siseWebClient.post()
            .uri("/api/auth/users_sise")
            .bodyValue(request)
            .retrieve()
            .bodyToMono(SiseAuthResponse::class.java)
            .map { it.tokens?.access ?: "" }
    }
}