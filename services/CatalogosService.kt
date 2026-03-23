package com.Ahorra.qsalud.services

import com.Ahorra.qsalud.models.brokers.CatalogoResponse
import com.Ahorra.qsalud.models.brokers.PreguntaCatalogo
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class CatalogosService(
    private val siseWebClient: WebClient
) {
    fun getCodigoPostal(cp: String, token: String): Mono<CatalogoResponse> {
        return siseWebClient.get()
            .uri("/api/catalogs/codpos/{cp}", cp) [cite: 523-524]
        .headers { it.setBearerAuth(token) }
            .retrieve()
            .bodyToMono(CatalogoResponse::class.java)
    }

    fun getListaCuestionarioMedico(token: String): Flux<PreguntaCatalogo> {
        return siseWebClient.get()
            .uri("/api/catalogs/typeQuestion/list/") [cite: 562-563]
        .headers { it.setBearerAuth(token) }
            .retrieve()
            .bodyToFlux(PreguntaCatalogo::class.java)
    }


    fun getPreguntaCuestionarioPorId(id: Int, token: String): Mono<PreguntaCatalogo> {
        return siseWebClient.get()
            .uri("/api/catalogs/typeQuestion/{id}/", id)
            .headers { it.setBearerAuth(token) }
            .retrieve().bodyToMono(PreguntaCatalogo::class.java)
    }

    fun getRegimenFiscal(token: String): Mono<String> {
        return siseWebClient.get()
            .uri("/api/catalogs/reg_fiscal")
            .headers { it.setBearerAuth(token) }
            .retrieve().bodyToMono(String::class.java)
    }

    fun getParentesco(token: String): Mono<String> {
        return siseWebClient.get()
            .uri("/api/catalogs/parentesco")
            .headers { it.setBearerAuth(token) }
            .retrieve().bodyToMono(String::class.java)
    }

    fun getParentescoBeneficiario(token: String): Mono<String> {
        return siseWebClient.get()
            .uri("/api/catalogs/parentesco_beneficiario")
            .headers { it.setBearerAuth(token) }
            .retrieve().bodyToMono(String::class.java)
    }

    fun getOcupaciones(token: String): Mono<String> {
        return siseWebClient.get()
            .uri("/api/catalogs/ocupacion") [cite: 626-627]
        .headers { it.setBearerAuth(token) }
            .retrieve()
            .bodyToMono(String::class.java)
    }
}