package com.Ahorra.qsalud.controllers

import com.Ahorra.qsalud.models.brokers.*
import com.Ahorra.qsalud.models.pagos.*
import com.Ahorra.qsalud.services.*
import org.springframework.core.io.Resource
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/api/v1/ahorra")
class OperacionesController(
    private val authService: QualitasAuthService,
    private val brokersService: BrokersService,
    private val documentosService: DocumentosService,
    private val linkPagoService: LinkPagoService,
    private val catalogosService: CatalogosService,
    private val archivoService: ArchivosService
) {


    // 1. OPERACIONES BROKERS (Cotizar / Emitir)

    @PostMapping("/brokers/crear")
    fun crearMovimiento(@RequestBody request: MovimientosRequest, @RequestHeader("X-Sise-User") user: String, @RequestHeader("X-Sise-Pass") pass: String): Mono<String> {
        return authService.getSiseAccessToken(user, pass).flatMap { token -> brokersService.crearMovimiento(request, token) }
    }


    // 2. DOCUMENTOS, FIRMAS Y OTP

    @PostMapping("/documentos/descargar")
    fun descargarDocumento(@RequestBody request: FormatPrintingRequest, @RequestHeader("X-Sise-User") user: String, @RequestHeader("X-Sise-Pass") pass: String): Mono<FormatPrintingResponse> {
        return authService.getSiseAccessToken(user, pass).flatMap { token -> documentosService.descargarFormato(request, token) }
    }

    @PostMapping("/documentos/descargar-firmada")
    fun descargarSolicitudFirmada(@RequestParam solicitud: String, @RequestHeader("X-Sise-User") user: String, @RequestHeader("X-Sise-Pass") pass: String): Mono<String> {
        return authService.getSiseAccessToken(user, pass).flatMap { token -> documentosService.descargarSolicitudFirmada(solicitud, token) }
    }

    @PostMapping("/otp/solicitar")
    fun solicitarToken(@RequestBody request: TokenRequest, @RequestHeader("X-Sise-User") user: String, @RequestHeader("X-Sise-Pass") pass: String): Mono<String> {
        return authService.getSiseAccessToken(user, pass).flatMap { token -> documentosService.solicitarToken(request, token) }
    }

    @PostMapping("/otp/validar")
    fun validarToken(@RequestBody request: VerifyTokenRequest, @RequestHeader("X-Sise-User") user: String, @RequestHeader("X-Sise-Pass") pass: String): Mono<String> {
        return authService.getSiseAccessToken(user, pass).flatMap { token -> documentosService.validarToken(request, token) }
    }

    @PostMapping("/firmas/guardar")
    fun guardarFirma(@RequestBody request: SignatureRequest, @RequestHeader("X-Sise-User") user: String, @RequestHeader("X-Sise-Pass") pass: String): Mono<String> {
        return authService.getSiseAccessToken(user, pass).flatMap { token -> documentosService.guardarFirma(request, token) }
    }

    @PostMapping("/cfdi/verificar")
    fun verificarCfdi(@RequestBody request: CfdiRequest, @RequestHeader("X-Sise-User") user: String, @RequestHeader("X-Sise-Pass") pass: String): Mono<String> {
        return authService.getSiseAccessToken(user, pass).flatMap { token -> documentosService.verificarCfdi(request, token) }
    }


    // 3. ARCHIVOS Y MULTIPART (INE, Domicilio)

    @PostMapping(value = ["/archivos/ine"], consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun subirIne(@RequestPart("file_front") fileFront: Resource, @RequestPart("file_back") fileBack: Resource, @RequestHeader("X-Sise-User") user: String, @RequestHeader("X-Sise-Pass") pass: String): Mono<String> {
        return authService.getSiseAccessToken(user, pass).flatMap { token -> archivoService.validarIdentificacion(fileFront, fileBack, token) }
    }


    // 4. LINK DE PAGO QSALUD

    @PostMapping("/pagos/generar-link")
    fun generarLinkPago(@RequestBody request: GenLinkRequest, @RequestHeader("X-Pago-User") user: String, @RequestHeader("X-Pago-Pass") pass: String): Mono<String> {
        return authService.getLinkPagoToken(user, pass).flatMap { token -> linkPagoService.generarLink(request, token) }
    }

    @PostMapping("/pagos/consultar-domiciliacion")
    fun consultarDomiciliacion(@RequestBody request: ConsulDomRequest, @RequestHeader("X-Pago-User") user: String, @RequestHeader("X-Pago-Pass") pass: String): Mono<String> {
        return authService.getLinkPagoToken(user, pass).flatMap { token -> linkPagoService.consultarDomiciliacion(request, token) }
    }

    @PostMapping("/pagos/buscar-recibos")
    fun buscarRecibos(@RequestBody request: PolizaRequest, @RequestHeader("X-Pago-User") user: String, @RequestHeader("X-Pago-Pass") pass: String): Mono<String> {
        return authService.getLinkPagoToken(user, pass).flatMap { token -> linkPagoService.searchReceipts(request, token) }
    }
}