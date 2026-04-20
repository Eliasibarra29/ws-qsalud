package com.Ahorra.qsalud.controllers

import com.Ahorra.qsalud.models.brokers.*
import com.Ahorra.qsalud.models.pagos.*
import com.Ahorra.qsalud.services.*
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.core.io.Resource
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.Base64

@RestController
@RequestMapping("/api/v1/ahorra")
@Tag(name = "API QSalud Ahorra", description = "Endpoints de integración con Quálitas Salud")
class OperacionesController(
    private val authService: QualitasAuthService,
    private val brokersService: BrokersService,
    private val documentosService: DocumentosService,
    private val linkPagoService: LinkPagoService,
    private val catalogosService: CatalogosService,
    private val archivoService: ArchivosService
) {

    private fun cleanToken(authHeader: String): String {
        return authHeader.replace("Bearer ", "").trim()
    }

    // ==========================================
    // 0. AUTENTICACIÓN (LOGIN)
    // ==========================================

    @Tag(name = "0. Autenticación")
    @Operation(summary = "Login SISE")
    @GetMapping("/auth/sise")
    fun loginSise(
        @Parameter(description = "Usuario SISE") @RequestHeader("X-Sise-User") user: String,
        @Parameter(description = "Password SISE") @RequestHeader("X-Sise-Pass") pass: String
    ): Mono<String> {
        return authService.getSiseAccessToken(user, pass)
    }

    @Tag(name = "0. Autenticación")
    @Operation(summary = "Login Pagos")
    @GetMapping("/auth/pagos")
    fun loginPagos(
        @Parameter(description = "Usuario Pagos") @RequestHeader("X-Pago-User") user: String,
        @Parameter(description = "Password Pagos") @RequestHeader("X-Pago-Pass") pass: String
    ): Mono<String> {
        return authService.getLinkPagoToken(user, pass)
    }

    // ==========================================
    // 1. CATÁLOGOS
    // ==========================================

    @Tag(name = "1. Catálogos")
    @SecurityRequirement(name = "BearerAuth")
    @Operation(summary = "Consultar Código Postal")
    @GetMapping("/catalogos/codpos/{cp}")
    fun getCodigoPostal(
        @PathVariable cp: String,
        @RequestHeader("Authorization") authHeader: String
    ): Mono<CodPosResponse> {
        return catalogosService.getCodPos(cp, cleanToken(authHeader))
    }

    @Tag(name = "1. Catálogos")
    @SecurityRequirement(name = "BearerAuth")
    @Operation(summary = "Listar Cuestionario Médico")
    @GetMapping("/catalogos/cuestionario")
    fun getCuestionarioMedico(@Parameter(hidden = true) @RequestHeader("Authorization") authHeader: String): Flux<PreguntaCatalogo> {
        return catalogosService.getListaCuestionarioMedico(cleanToken(authHeader))
    }

    @Tag(name = "1. Catálogos")
    @SecurityRequirement(name = "BearerAuth")
    @Operation(summary = "Consultar Pregunta Médica por ID")
    @GetMapping("/catalogos/cuestionario/{id}")
    fun getCuestionarioPorId(
        @Parameter(description = "ID de la pregunta") @PathVariable id: Int,
        @Parameter(hidden = true) @RequestHeader("Authorization") authHeader: String
    ): Mono<PreguntaCatalogo> {
        return catalogosService.getPreguntaCuestionarioPorId(id, cleanToken(authHeader))
    }

    @Tag(name = "1. Catálogos")
    @SecurityRequirement(name = "BearerAuth")
    @Operation(summary = "Listar Ocupaciones")
    @GetMapping("/catalogos/ocupaciones")
    fun getOcupaciones(@Parameter(hidden = true) @RequestHeader("Authorization") authHeader: String): Mono<String> {
        return catalogosService.getOcupaciones(cleanToken(authHeader))
    }

    @Tag(name = "1. Catálogos")
    @SecurityRequirement(name = "BearerAuth")
    @Operation(summary = "Listar Régimen Fiscal")
    @GetMapping("/catalogos/regimen-fiscal")
    fun getRegimenFiscal(@Parameter(hidden = true) @RequestHeader("Authorization") authHeader: String): Mono<String> {
        return catalogosService.getRegimenFiscal(cleanToken(authHeader))
    }

    @Tag(name = "1. Catálogos")
    @SecurityRequirement(name = "BearerAuth")
    @Operation(summary = "Listar Parentescos")
    @GetMapping("/catalogos/parentesco")
    fun getParentesco(@Parameter(hidden = true) @RequestHeader("Authorization") authHeader: String): Mono<String> {
        return catalogosService.getParentesco(cleanToken(authHeader))
    }

    @Tag(name = "1. Catálogos")
    @SecurityRequirement(name = "BearerAuth")
    @Operation(summary = "Listar Parentescos Beneficiarios")
    @GetMapping("/catalogos/parentesco-beneficiario")
    fun getParentescoBeneficiario(@Parameter(hidden = true) @RequestHeader("Authorization") authHeader: String): Mono<String> {
        return catalogosService.getParentescoBeneficiario(cleanToken(authHeader))
    }

    // ==========================================
    // 2. MOTOR DE BROKERS (EMISIÓN Y COTIZACIÓN)
    // ==========================================

    @Tag(name = "2. Emisión (Brokers)")
    @SecurityRequirement(name = "BearerAuth")
    @Operation(summary = "Crear Movimiento (JSON Crudo)")
    @PostMapping("/brokers/crear")
    fun crearMovimiento(
        @RequestBody request: MovimientosRequest,
        @Parameter(hidden = true) @RequestHeader("Authorization") authHeader: String
    ): Mono<String> {
        return brokersService.crearMovimiento(request, cleanToken(authHeader))
    }

    @Tag(name = "2. Emisión (Brokers)")
    @SecurityRequirement(name = "BearerAuth")
    @Operation(
        summary = "Procesar Movimiento (Dinámico: Cotizar, Solicitud, Emisión)",
        description = "Usa tipoMovimiento: '2' (Cotizar), '7' (Solicitud) o '3' (Emitir)."
    )
    @PostMapping("/brokers/procesar-movimiento")
    fun procesarMovimiento(
        @RequestBody request: CotizacionSimplificadaRequest,
        @Parameter(hidden = true) @RequestHeader("Authorization") authHeader: String
    ): Mono<String> {
        return brokersService.procesarMovimientoDinamico(request, cleanToken(authHeader))
    }

    // ==========================================
    // 3. DOCUMENTOS Y FIRMAS
    // ==========================================

    @Tag(name = "3. Documentos y Firmas")
    @SecurityRequirement(name = "BearerAuth")
    @Operation(summary = "Descargar Documentos (Cotización, Emisión, Solicitud)")
    @PostMapping("/documentos/descargar", produces = [MediaType.APPLICATION_PDF_VALUE])
    fun descargarDocumento(
        @RequestBody request: FormatPrintingRequest,
        @Parameter(hidden = true) @RequestHeader("Authorization") authHeader: String
    ): Mono<ResponseEntity<ByteArray>> {
        val inputSolicitud = request.solicitud ?: ""
        val folioCompleto = if (inputSolicitud.length == 10) "37$inputSolicitud" else inputSolicitud
        val requestFinal = FormatPrintingRequest(solicitud = folioCompleto)

        return documentosService.descargarFormato(requestFinal, cleanToken(authHeader))
            .map { response ->
                val pdfBytes = Base64.getDecoder().decode(response.base64 ?: "")
                ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"documento_$folioCompleto.pdf\"")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(pdfBytes)
            }
    }

    @Tag(name = "3. Documentos y Firmas")
    @SecurityRequirement(name = "BearerAuth")
    @Operation(summary = "Descargar Solicitud Firmada")
    @PostMapping("/documentos/descargar-firmada", produces = [MediaType.APPLICATION_PDF_VALUE])
    fun descargarSolicitudFirmada(
        @RequestParam solicitud: String,
        @Parameter(hidden = true) @RequestHeader("Authorization") authHeader: String
    ): Mono<ResponseEntity<ByteArray>> {
        return documentosService.descargarSolicitudFirmada(solicitud, cleanToken(authHeader))
            .map { pdfBytes ->
                ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"solicitud_$solicitud.pdf\"")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(pdfBytes)
            }
    }

    @Tag(name = "3. Documentos y Firmas")
    @SecurityRequirement(name = "BearerAuth")
    @Operation(summary = "Solicitar Token OTP")
    @PostMapping("/otp/solicitar")
    fun solicitarToken(@RequestBody request: TokenRequest, @Parameter(hidden = true) @RequestHeader("Authorization") authHeader: String): Mono<String> {
        return documentosService.solicitarToken(request, cleanToken(authHeader))
    }

    @Tag(name = "3. Documentos y Firmas")
    @SecurityRequirement(name = "BearerAuth")
    @Operation(summary = "Validar Token OTP")
    @PostMapping("/otp/validar")
    fun validarToken(@RequestBody request: VerifyTokenRequest, @Parameter(hidden = true) @RequestHeader("Authorization") authHeader: String): Mono<String> {
        return documentosService.validarToken(request, cleanToken(authHeader))
    }

    @Tag(name = "3. Documentos y Firmas")
    @SecurityRequirement(name = "BearerAuth")
    @Operation(summary = "Guardar Firma Biométrica")
    @PostMapping("/firmas/guardar")
    fun guardarFirma(@RequestBody request: SignatureRequest, @Parameter(hidden = true) @RequestHeader("Authorization") authHeader: String): Mono<String> {
        return documentosService.guardarFirma(request, cleanToken(authHeader))
    }

    @Tag(name = "3. Documentos y Firmas")
    @SecurityRequirement(name = "BearerAuth")
    @Operation(summary = "Verificar CFDI")
    @PostMapping("/cfdi/verificar")
    fun verificarCfdi(@RequestBody request: CfdiRequest, @Parameter(hidden = true) @RequestHeader("Authorization") authHeader: String): Mono<String> {
        return documentosService.verificarCfdi(request, cleanToken(authHeader))
    }

    // ==========================================
    // 4. ARCHIVOS
    // ==========================================

    @Tag(name = "4. Validación de Archivos")
    @SecurityRequirement(name = "BearerAuth")
    @Operation(summary = "Validar INE")
    @PostMapping(value = ["/archivos/ine"], consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun subirIne(
        @RequestPart("file_front") fileFront: Resource,
        @RequestPart("file_back") fileBack: Resource,
        @Parameter(hidden = true) @RequestHeader("Authorization") authHeader: String
    ): Mono<String> {
        return archivoService.validarIdentificacion(fileFront, fileBack, cleanToken(authHeader))
    }

    @Tag(name = "4. Validación de Archivos")
    @SecurityRequirement(name = "BearerAuth")
    @Operation(summary = "Validar Comprobante Domicilio")
    @PostMapping(value = ["/archivos/domicilio"], consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun subirDomicilio(
        @RequestPart("file") file: Resource,
        @Parameter(hidden = true) @RequestHeader("Authorization") authHeader: String
    ): Mono<String> {
        return archivoService.validarComprobanteDomicilio(file, cleanToken(authHeader))
    }

    @Tag(name = "4. Validación de Archivos")
    @SecurityRequirement(name = "BearerAuth")
    @Operation(summary = "Validar Video")
    @PostMapping(value = ["/archivos/video"], consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun subirVideo(
        @RequestPart("image_front") imageFront: Resource,
        @RequestPart("image_back") imageBack: Resource,
        @RequestPart("video") video: Resource,
        @Parameter(hidden = true) @RequestHeader("Authorization") authHeader: String
    ): Mono<String> {
        return archivoService.validarVideo(imageFront, imageBack, video, cleanToken(authHeader))
    }

    // ==========================================
    // 5. LINK DE PAGO QSALUD
    // ==========================================

    @Tag(name = "5. Link de Pago")
    @SecurityRequirement(name = "BearerAuth")
    @Operation(summary = "Generar Link de Pago")
    @PostMapping("/pagos/generar-link")
    fun generarLinkPago(@RequestBody request: GenLinkRequest, @Parameter(hidden = true) @RequestHeader("Authorization") authHeader: String): Mono<String> {
        return linkPagoService.generarLink(request, cleanToken(authHeader))
    }

    @Tag(name = "5. Link de Pago")
    @SecurityRequirement(name = "BearerAuth")
    @Operation(summary = "Consultar Domiciliación")
    @PostMapping("/pagos/consultar-domiciliacion")
    fun consultarDomiciliacion(@RequestBody request: ConsulDomRequest, @Parameter(hidden = true) @RequestHeader("Authorization") authHeader: String): Mono<String> {
        return linkPagoService.consultarDomiciliacion(request, cleanToken(authHeader))
    }

    @Tag(name = "5. Link de Pago")
    @SecurityRequirement(name = "BearerAuth")
    @Operation(summary = "Buscar Recibos")
    @PostMapping("/pagos/buscar-recibos")
    fun buscarRecibos(@RequestBody request: PolizaRequest, @Parameter(hidden = true) @RequestHeader("Authorization") authHeader: String): Mono<String> {
        return linkPagoService.searchReceipts(request, cleanToken(authHeader))
    }

    @Tag(name = "5. Link de Pago")
    @SecurityRequirement(name = "BearerAuth")
    @Operation(summary = "Validar BIN")
    @PostMapping("/pagos/valida-bin")
    fun validaBin(@RequestBody request: ValidaBinRequest, @Parameter(hidden = true) @RequestHeader("Authorization") authHeader: String): Mono<String> {
        return linkPagoService.validaBin(request, cleanToken(authHeader))
    }

    @Tag(name = "5. Link de Pago")
    @SecurityRequirement(name = "BearerAuth")
    @Operation(summary = "Buscar Link Existente")
    @PostMapping("/pagos/buscar-link")
    fun searchLink(@RequestBody request: PolizaRequest, @Parameter(hidden = true) @RequestHeader("Authorization") authHeader: String): Mono<String> {
        return linkPagoService.searchLink(request, cleanToken(authHeader))
    }

    @Tag(name = "5. Link de Pago")
    @SecurityRequirement(name = "BearerAuth")
    @Operation(summary = "Cancelar Link")
    @PostMapping("/pagos/cancelar-link")
    fun cancelLink(@RequestBody request: CancelLinkRequest, @Parameter(hidden = true) @RequestHeader("Authorization") authHeader: String): Mono<String> {
        return linkPagoService.cancelLink(request, cleanToken(authHeader))
    }

    @Tag(name = "5. Link de Pago")
    @SecurityRequirement(name = "BearerAuth")
    @Operation(summary = "Generar WebPay")
    @PostMapping("/pagos/webpay")
    fun genWebPay(@RequestBody request: GenWebPayRequest, @Parameter(hidden = true) @RequestHeader("Authorization") authHeader: String): Mono<String> {
        return linkPagoService.genWebPay(request, cleanToken(authHeader))
    }

    @Tag(name = "5. Link de Pago")
    @SecurityRequirement(name = "BearerAuth")
    @Operation(summary = "Consultar Recibo FA")
    @PostMapping("/pagos/fareceipt")
    fun fareceipt(@RequestBody request: PolizaRequest, @Parameter(hidden = true) @RequestHeader("Authorization") authHeader: String): Mono<String> {
        return linkPagoService.fareceipt(request, cleanToken(authHeader))
    }
}