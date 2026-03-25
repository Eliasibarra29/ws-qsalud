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

    // Función auxiliar para limpiar el token por si el cliente envía "Bearer " al principio
    private fun cleanToken(authHeader: String): String {
        return authHeader.replace("Bearer ", "").trim()
    }


    // ==========================================
    // 0. AUTENTICACIÓN (LOGIN)
    // ==========================================

    @GetMapping("/auth/sise")
    fun loginSise(@RequestHeader("X-Sise-User") user: String, @RequestHeader("X-Sise-Pass") pass: String): Mono<String> {
        return authService.getSiseAccessToken(user, pass)
    }

    @GetMapping("/auth/pagos")
    fun loginPagos(@RequestHeader("X-Pago-User") user: String, @RequestHeader("X-Pago-Pass") pass: String): Mono<String> {
        return authService.getLinkPagoToken(user, pass)
    }


    // ==========================================
    // 1. CATÁLOGOS
    // ==========================================

    @GetMapping("/catalogos/codpos/{cp}")
    fun getCodigoPostal(@PathVariable cp: String, @RequestHeader("Authorization") authHeader: String): Mono<CatalogoResponse> {
        return catalogosService.getCodigoPostal(cp, cleanToken(authHeader))
    }

    @GetMapping("/catalogos/cuestionario")
    fun getCuestionarioMedico(@RequestHeader("Authorization") authHeader: String): Flux<PreguntaCatalogo> {
        return catalogosService.getListaCuestionarioMedico(cleanToken(authHeader))
    }

    @GetMapping("/catalogos/cuestionario/{id}")
    fun getCuestionarioPorId(@PathVariable id: Int, @RequestHeader("Authorization") authHeader: String): Mono<PreguntaCatalogo> {
        return catalogosService.getPreguntaCuestionarioPorId(id, cleanToken(authHeader))
    }

    @GetMapping("/catalogos/ocupaciones")
    fun getOcupaciones(@RequestHeader("Authorization") authHeader: String): Mono<String> {
        return catalogosService.getOcupaciones(cleanToken(authHeader))
    }

    @GetMapping("/catalogos/regimen-fiscal")
    fun getRegimenFiscal(@RequestHeader("Authorization") authHeader: String): Mono<String> {
        return catalogosService.getRegimenFiscal(cleanToken(authHeader))
    }

    @GetMapping("/catalogos/parentesco")
    fun getParentesco(@RequestHeader("Authorization") authHeader: String): Mono<String> {
        return catalogosService.getParentesco(cleanToken(authHeader))
    }

    @GetMapping("/catalogos/parentesco-beneficiario")
    fun getParentescoBeneficiario(@RequestHeader("Authorization") authHeader: String): Mono<String> {
        return catalogosService.getParentescoBeneficiario(cleanToken(authHeader))
    }


    // ==========================================
    // 2. OPERACIONES BROKERS (Cotizar / Emitir)
    // ==========================================

    @PostMapping("/brokers/crear")
    fun crearMovimiento(@RequestBody request: MovimientosRequest, @RequestHeader("Authorization") authHeader: String): Mono<String> {
        return brokersService.crearMovimiento(request, cleanToken(authHeader))
    }


    // ==========================================
    // 3. DOCUMENTOS, FIRMAS Y OTP
    // ==========================================

    @PostMapping("/documentos/descargar")
    fun descargarDocumento(@RequestBody request: FormatPrintingRequest, @RequestHeader("Authorization") authHeader: String): Mono<FormatPrintingResponse> {
        return documentosService.descargarFormato(request, cleanToken(authHeader))
    }

    @PostMapping("/documentos/descargar-firmada")
    fun descargarSolicitudFirmada(@RequestParam solicitud: String, @RequestHeader("Authorization") authHeader: String): Mono<String> {
        return documentosService.descargarSolicitudFirmada(solicitud, cleanToken(authHeader))
    }

    @PostMapping("/otp/solicitar")
    fun solicitarToken(@RequestBody request: TokenRequest, @RequestHeader("Authorization") authHeader: String): Mono<String> {
        return documentosService.solicitarToken(request, cleanToken(authHeader))
    }

    @PostMapping("/otp/validar")
    fun validarToken(@RequestBody request: VerifyTokenRequest, @RequestHeader("Authorization") authHeader: String): Mono<String> {
        return documentosService.validarToken(request, cleanToken(authHeader))
    }

    @PostMapping("/firmas/guardar")
    fun guardarFirma(@RequestBody request: SignatureRequest, @RequestHeader("Authorization") authHeader: String): Mono<String> {
        return documentosService.guardarFirma(request, cleanToken(authHeader))
    }

    @PostMapping("/cfdi/verificar")
    fun verificarCfdi(@RequestBody request: CfdiRequest, @RequestHeader("Authorization") authHeader: String): Mono<String> {
        return documentosService.verificarCfdi(request, cleanToken(authHeader))
    }


    // ==========================================
    // 4. ARCHIVOS  (INE, Domicilio, Video)
    // ==========================================

    @PostMapping(value = ["/archivos/ine"], consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun subirIne(@RequestPart("file_front") fileFront: Resource, @RequestPart("file_back") fileBack: Resource, @RequestHeader("Authorization") authHeader: String): Mono<String> {
        return archivoService.validarIdentificacion(fileFront, fileBack, cleanToken(authHeader))
    }

    @PostMapping(value = ["/archivos/domicilio"], consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun subirDomicilio(@RequestPart("file") file: Resource, @RequestHeader("Authorization") authHeader: String): Mono<String> {
        return archivoService.validarComprobanteDomicilio(file, cleanToken(authHeader))
    }

    @PostMapping(value = ["/archivos/video"], consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun subirVideo(@RequestPart("image_front") imageFront: Resource, @RequestPart("image_back") imageBack: Resource, @RequestPart("video") video: Resource, @RequestHeader("Authorization") authHeader: String): Mono<String> {
        return archivoService.validarVideo(imageFront, imageBack, video, cleanToken(authHeader))
    }


    // ==========================================
    // 5. LINK DE PAGO QSALUD
    // ==========================================

    @PostMapping("/pagos/generar-link")
    fun generarLinkPago(@RequestBody request: GenLinkRequest, @RequestHeader("Authorization") authHeader: String): Mono<String> {
        return linkPagoService.generarLink(request, cleanToken(authHeader))
    }

    @PostMapping("/pagos/consultar-domiciliacion")
    fun consultarDomiciliacion(@RequestBody request: ConsulDomRequest, @RequestHeader("Authorization") authHeader: String): Mono<String> {
        return linkPagoService.consultarDomiciliacion(request, cleanToken(authHeader))
    }

    @PostMapping("/pagos/buscar-recibos")
    fun buscarRecibos(@RequestBody request: PolizaRequest, @RequestHeader("Authorization") authHeader: String): Mono<String> {
        return linkPagoService.searchReceipts(request, cleanToken(authHeader))
    }

    @PostMapping("/pagos/valida-bin")
    fun validaBin(@RequestBody request: ValidaBinRequest, @RequestHeader("Authorization") authHeader: String): Mono<String> {
        return linkPagoService.validaBin(request, cleanToken(authHeader))
    }

    @PostMapping("/pagos/buscar-link")
    fun searchLink(@RequestBody request: PolizaRequest, @RequestHeader("Authorization") authHeader: String): Mono<String> {
        return linkPagoService.searchLink(request, cleanToken(authHeader))
    }

    @PostMapping("/pagos/cancelar-link")
    fun cancelLink(@RequestBody request: CancelLinkRequest, @RequestHeader("Authorization") authHeader: String): Mono<String> {
        return linkPagoService.cancelLink(request, cleanToken(authHeader))
    }

    @PostMapping("/pagos/webpay")
    fun genWebPay(@RequestBody request: GenWebPayRequest, @RequestHeader("Authorization") authHeader: String): Mono<String> {
        return linkPagoService.genWebPay(request, cleanToken(authHeader))
    }

    @PostMapping("/pagos/fareceipt")
    fun fareceipt(@RequestBody request: PolizaRequest, @RequestHeader("Authorization") authHeader: String): Mono<String> {
        return linkPagoService.fareceipt(request, cleanToken(authHeader))
    }
}