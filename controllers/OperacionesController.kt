package com.Ahorra.qsalud.controllers

import com.Ahorra.qsalud.models.brokers.FormatPrintingRequest
import com.Ahorra.qsalud.models.brokers.FormatPrintingResponse
import com.Ahorra.qsalud.models.brokers.MovimientosRequest
import com.Ahorra.qsalud.models.pagos.GenLinkRequest
import com.Ahorra.qsalud.services.BrokersService
import com.Ahorra.qsalud.services.DocumentosService
import com.Ahorra.qsalud.services.LinkPagoService
import com.Ahorra.qsalud.services.QualitasAuthService
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/api/v1/ahorra")
class OperacionesController(
    private val authService: QualitasAuthService,
    private val brokersService: BrokersService,
    private val documentosService: DocumentosService,
    private val linkPagoService: LinkPagoService
) {

    // --- OPERACIONES BROKERS ---

    @PostMapping("/brokers/crear")
    fun crearMovimiento(
        @RequestBody request: MovimientosRequest,
        @RequestHeader("X-Sise-User") user: String,
        @RequestHeader("X-Sise-Pass") pass: String
    ): Mono<String> {
        return authService.getSiseAccessToken(user, pass)
            .flatMap { token -> brokersService.crearMovimiento(request, token) }
    }

    @PostMapping("/documentos/descargar")
    fun descargarDocumento(
        @RequestBody request: FormatPrintingRequest,
        @RequestHeader("X-Sise-User") user: String,
        @RequestHeader("X-Sise-Pass") pass: String
    ): Mono<FormatPrintingResponse> {
        return authService.getSiseAccessToken(user, pass)
            .flatMap { token -> documentosService.descargarFormato(request, token) }
    }

    // --- PAGOS ---

    @PostMapping("/pagos/generar-link")
    fun generarLinkPago(
        @RequestBody request: GenLinkRequest,
        @RequestHeader("X-Pago-User") user: String,
        @RequestHeader("X-Pago-Pass") pass: String
    ): Mono<String> {
        return authService.getLinkPagoToken(user, pass)
            .flatMap { token -> linkPagoService.generarLink(request, token) }
    }
}