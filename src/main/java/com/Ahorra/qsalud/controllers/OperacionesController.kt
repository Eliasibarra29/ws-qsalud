package com.Ahorra.qsalud.controllers

import com.Ahorra.qsalud.models.brokers.*
import com.Ahorra.qsalud.services.*
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/api/v1/ahorra")
class OperacionesController(
    private val brokersService: BrokersService,
    private val catalogosService: CatalogosService
) {

    private fun cleanToken(authHeader: String): String = authHeader.replace("Bearer ", "").trim()

    @GetMapping("/catalogos/codpos/{cp}")
    fun getCodigoPostal(@PathVariable cp: String, @RequestHeader("Authorization") authHeader: String): Mono<CodPosResponse> =
        catalogosService.getCodPos(cp, cleanToken(authHeader))

    @GetMapping("/catalogos/ocupaciones")
    fun getOcupaciones(@RequestHeader("Authorization") authHeader: String): Mono<String> =
        catalogosService.getOcupaciones(cleanToken(authHeader))

    @GetMapping("/catalogos/parentesco")
    fun getParentesco(@RequestHeader("Authorization") authHeader: String): Mono<String> =
        catalogosService.getParentesco(cleanToken(authHeader))

    @GetMapping("/catalogos/cuestionario")
    fun getCuestionarioMedico(@RequestHeader("Authorization") authHeader: String): Flux<PreguntaCatalogo> =
        catalogosService.getListaCuestionarioMedico(cleanToken(authHeader))

    @PostMapping("/brokers/cotizar-rapido")
    fun cotizarRapido(
        @RequestBody request: CotizacionSimplificadaRequest,
        @RequestHeader("Authorization") authHeader: String
    ): Mono<String> = brokersService.cotizarRapido(request, cleanToken(authHeader))
}