package com.Ahorra.qsalud.controllers

import com.Ahorra.qsalud.models.brokers.*
import com.Ahorra.qsalud.models.pagos.*
import com.Ahorra.qsalud.services.*
import org.springframework.core.io.Resource
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
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


    // 1. CATÁLOGOS

    @GetMapping("/catalogos/codpos/{cp}")
    fun getCodigoPostal(@PathVariable cp: String, @RequestHeader("X-Sise-User") user: String, @RequestHeader("X-Sise-Pass") pass: String): Mono<CatalogoResponse> {
        return authService.getSiseAccessToken(user, pass).flatMap { token -> catalogosService.getCodigoPostal(cp, token) }
    }

    @GetMapping("/catalogos/cuestionario")
    fun getCuestionarioMedico(@RequestHeader("X-Sise-User") user: String, @RequestHeader("X-Sise-Pass") pass: String): Flux<PreguntaCatalogo> {
        return authService.getSiseAccessToken(user, pass).flatMapMany { token -> catalogosService.getListaCuestionarioMedico(token) }
    }

    @GetMapping("/catalogos/cuestionario/{id}")
    fun getCuestionarioPorId(@PathVariable id: Int, @RequestHeader("X-Sise-User") user: String, @RequestHeader("X-Sise-Pass") pass: String): Mono<PreguntaCatalogo> {
        return authService.getSiseAccessToken(user, pass).flatMap { token -> catalogosService.getPreguntaCuestionarioPorId(id, token) }
    }

    @GetMapping("/catalogos/ocupaciones")
    fun getOcupaciones(@RequestHeader("X-Sise-User") user: String, @RequestHeader("X-Sise-Pass") pass: String): Mono<String> {
        return authService.getSiseAccessToken(user, pass).flatMap { token -> catalogosService.getOcupaciones(token) }
    }

    @GetMapping("/catalogos/regimen-fiscal")
    fun getRegimenFiscal(@RequestHeader("X-Sise-User") user: String, @RequestHeader("X-Sise-Pass") pass: String): Mono<String> {
        return authService.getSiseAccessToken(user, pass).flatMap { token -> catalogosService.getRegimenFiscal(token) }
    }

    @GetMapping("/catalogos/parentesco")
    fun getParentesco(@RequestHeader("X-Sise-User") user: String, @RequestHeader("X-Sise-Pass") pass: String): Mono<String> {
        return authService.getSiseAccessToken(user, pass).flatMap { token -> catalogosService.getParentesco(token) }
    }

    @GetMapping("/catalogos/parentesco-beneficiario")
    fun getParentescoBeneficiario(@RequestHeader("X-Sise-User") user: String, @RequestHeader("X-Sise-Pass") pass: String): Mono<String> {
        return authService.getSiseAccessToken(user, pass).flatMap { token -> catalogosService.getParentescoBeneficiario(token) }
    }



    // 2. OPERACIONES BROKERS (Cotizar / Emitir)

    @PostMapping("/brokers/crear")
    fun crearMovimiento(@RequestBody request: MovimientosRequest, @RequestHeader("X-Sise-User") user: String, @RequestHeader("X-Sise-Pass") pass: String): Mono<String> {
        return authService.getSiseAccessToken(user, pass).flatMap { token -> brokersService.crearMovimiento(request, token) }
    }



    // 3. DOCUMENTOS, FIRMAS Y OTP

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



    // 4. ARCHIVOS  (INE, Domicilio, Video)

    @PostMapping(value = ["/archivos/ine"], consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun subirIne(@RequestPart("file_front") fileFront: Resource, @RequestPart("file_back") fileBack: Resource, @RequestHeader("X-Sise-User") user: String, @RequestHeader("X-Sise-Pass") pass: String): Mono<String> {
        return authService.getSiseAccessToken(user, pass).flatMap { token -> archivoService.validarIdentificacion(fileFront, fileBack, token) }
    }

    @PostMapping(value = ["/archivos/domicilio"], consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun subirDomicilio(@RequestPart("file") file: Resource, @RequestHeader("X-Sise-User") user: String, @RequestHeader("X-Sise-Pass") pass: String): Mono<String> {
        return authService.getSiseAccessToken(user, pass).flatMap { token -> archivoService.validarComprobanteDomicilio(file, token) }
    }

    @PostMapping(value = ["/archivos/video"], consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun subirVideo(@RequestPart("image_front") imageFront: Resource, @RequestPart("image_back") imageBack: Resource, @RequestPart("video") video: Resource, @RequestHeader("X-Sise-User") user: String, @RequestHeader("X-Sise-Pass") pass: String): Mono<String> {
        return authService.getSiseAccessToken(user, pass).flatMap { token -> archivoService.validarVideo(imageFront, imageBack, video, token) }
    }



    // 5. LINK DE PAGO QSALUD

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

    @PostMapping("/pagos/valida-bin")
    fun validaBin(@RequestBody request: ValidaBinRequest, @RequestHeader("X-Pago-User") user: String, @RequestHeader("X-Pago-Pass") pass: String): Mono<String> {
        return authService.getLinkPagoToken(user, pass).flatMap { token -> linkPagoService.validaBin(request, token) }
    }

    @PostMapping("/pagos/buscar-link")
    fun searchLink(@RequestBody request: PolizaRequest, @RequestHeader("X-Pago-User") user: String, @RequestHeader("X-Pago-Pass") pass: String): Mono<String> {
        return authService.getLinkPagoToken(user, pass).flatMap { token -> linkPagoService.searchLink(request, token) }
    }

    @PostMapping("/pagos/cancelar-link")
    fun cancelLink(@RequestBody request: CancelLinkRequest, @RequestHeader("X-Pago-User") user: String, @RequestHeader("X-Pago-Pass") pass: String): Mono<String> {
        return authService.getLinkPagoToken(user, pass).flatMap { token -> linkPagoService.cancelLink(request, token) }
    }

    @PostMapping("/pagos/webpay")
    fun genWebPay(@RequestBody request: GenWebPayRequest, @RequestHeader("X-Pago-User") user: String, @RequestHeader("X-Pago-Pass") pass: String): Mono<String> {
        return authService.getLinkPagoToken(user, pass).flatMap { token -> linkPagoService.genWebPay(request, token) }
    }

    @PostMapping("/pagos/fareceipt")
    fun fareceipt(@RequestBody request: PolizaRequest, @RequestHeader("X-Pago-User") user: String, @RequestHeader("X-Pago-Pass") pass: String): Mono<String> {
        return authService.getLinkPagoToken(user, pass).flatMap { token -> linkPagoService.fareceipt(request, token) }
    }
}