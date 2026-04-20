package com.Ahorra.qsalud.models.brokers

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema

// --- ESTRUCTURA QUÁLITAS SISE (PARA COTIZAR/EMITIR) ---
@JsonInclude(JsonInclude.Include.NON_NULL)
data class MovimientosRequest(@JsonProperty("Movimientos") val movimientos: List<MovimientoWrapper>)

@JsonInclude(JsonInclude.Include.NON_NULL)
data class MovimientoWrapper(@JsonProperty("Movimiento") val detalle: Movimiento)

@JsonInclude(JsonInclude.Include.NON_NULL)
data class Movimiento(
    @JsonProperty("@TipoMovimiento") val tipoMovimiento: String,
    @JsonProperty("@NoNegocio") val noNegocio: String? = null,
    @JsonProperty("@Usuario") val usuario: String? = null,
    @JsonProperty("@NoCotizacion") val noCotizacion: String? = null,
    @JsonProperty("@Solicitud") val solicitud: String? = null,
    @JsonProperty("@SubRamo") val subRamo: String? = null,
    @JsonProperty("DatosGenerales") val datosGenerales: DatosGenerales? = null,
    @JsonProperty("Contratante") val contratante: Contratante? = null,
    @JsonProperty("Asegurados") val asegurados: AseguradosWrapper? = null
)

@JsonInclude(JsonInclude.Include.NON_NULL)
data class DatosGenerales(
    @JsonProperty("FechaInicio") val fechaInicio: String,
    @JsonProperty("FechaTermino") val fechaTermino: String,
    @JsonProperty("Moneda") val moneda: Int,
    @JsonProperty("Agente") val agente: Int,
    @JsonProperty("FormaPago") val formaPago: String,
    @JsonProperty("CodigoOficina") val codigoOficina: Int,
    @JsonProperty("CodigoProducto") val codigoProducto: Long,
    @JsonProperty("NivelProducto") val nivelProducto: Int,
    @JsonProperty("PeriodoGracia") val periodoGracia: Int,
    @JsonProperty("OrigenRecursos") val origenRecursos: Int,
    @JsonProperty("AutorizacionInfoMedica") val autorizacionInfoMedica: String,
    @JsonProperty("DocumentacionCorreo") val documentacionCorreo: String,
    @JsonProperty("CPIVA") val cpIva: String
)

@JsonInclude(JsonInclude.Include.NON_NULL)
data class Contratante(
    @JsonProperty("@TipoPersona") val tipoPersona: String,
    @JsonProperty("@NacionalidadContra") val nacionalidadContra: String,
    @JsonProperty("@RFCContra") val rfcContra: String,
    @JsonProperty("NombreContratante") val nombreContratante: String,
    @JsonProperty("APContratante") val apContratante: String,
    @JsonProperty("AMContratante") val amContratante: String,
    @JsonProperty("FechaNacimientoContratante") val fechaNacimientoContratante: String,
    @JsonProperty("GeneroContratante") val generoContratante: String,
    @JsonProperty("CalleContratante") val calleContratante: String,
    @JsonProperty("ColoniaContratante") val coloniaContratante: String,
    @JsonProperty("MunicipioContratante") val municipioContratante: Int,
    @JsonProperty("EstadoContratante") val estadoContratante: Int,
    @JsonProperty("CPContratante") val cpContratante: String,
    @JsonProperty("PaisNacimientoContra") val paisNacimientoContra: Int,
    @JsonProperty("EntidadFedNacimientoContra") val entidadFedNacimientoContra: Int,
    @JsonProperty("TipoDomicilioContra") val tipoDomicilioContra: Int,
    @JsonProperty("NoExteriorContra") val noExteriorContra: String,
    @JsonProperty("CelularContratante") val celularContratante: Long,
    @JsonProperty("eMailContratante") val emailContratante: String,
    @JsonProperty("OcupacionContratante") val ocupacionContratante: Int,

    // --- CAMPOS NUEVOS AGREGADOS ---
    @JsonProperty("eMailFactura") val emailFactura: String? = null,
    @JsonProperty("CPFactura") val cpFactura: String? = null,

    @JsonProperty("Cuestionario492") val cuestionario492: Cuestionario492? = null
)

@JsonInclude(JsonInclude.Include.NON_NULL)
data class AseguradosWrapper(@JsonProperty("Asegurado") val aseguradoList: List<Asegurado>)

@JsonInclude(JsonInclude.Include.NON_NULL)
data class Asegurado(
    @JsonProperty("@Certificado") val certificado: String,
    @JsonProperty("@TipoAsegurado") val tipoAsegurado: String,
    @JsonProperty("@Parentesco") val parentesco: String,
    @JsonProperty("@NoInciso") val noInciso: String,
    @JsonProperty("DatosAsegurado") val datosAsegurado: DatosAsegurado,
    @JsonProperty("Beneficiarios") val beneficiarios: BeneficiariosWrapper? = null,
    @JsonProperty("Cuestionarios") val cuestionarios: CuestionariosAsegurado? = null,
    @JsonProperty("Coberturas") val coberturas: CoberturasWrapper
)

@JsonInclude(JsonInclude.Include.NON_NULL)
data class DatosAsegurado(
    @JsonProperty("NombreAsegurado") val nombreAsegurado: String,
    @JsonProperty("ApellidoPatAsegurado") val apellidoPatAsegurado: String,
    @JsonProperty("ApellidoMatAsegurado") val apellidoMatAsegurado: String,
    @JsonProperty("Nacional") val nacional: String,
    @JsonProperty("Genero") val genero: String,
    @JsonProperty("FechaNacimiento") val fechaNacimiento: String,
    @JsonProperty("RFCAsegurado") val rfcAsegurado: String,
    @JsonProperty("eMail") val email: String,
    @JsonProperty("Celular") val celular: Long,
    @JsonProperty("PaisNacimientoAsegurado") val paisNacimientoAsegurado: Int,
    @JsonProperty("OcupacionAsegurado") val ocupacionAsegurado: Int
)

@JsonInclude(JsonInclude.Include.NON_NULL)
data class BeneficiariosWrapper(@JsonProperty("Beneficiario") val beneficiarioList: List<Beneficiario>)

@JsonInclude(JsonInclude.Include.NON_NULL)
data class Beneficiario(
    @JsonProperty("@NoBeneficiario") val noBeneficiario: String,
    @JsonProperty("NombreBeneficiario") val nombreBeneficiario: String,
    @JsonProperty("ApellidoPatBeneficiario") val apellidoPatBeneficiario: String,
    @JsonProperty("ApellidoMatBeneficiario") val apellidoMatBeneficiario: String,
    @JsonProperty("FechaNacimientoBeneficiario") val fechaNacimientoBeneficiario: String,
    @JsonProperty("ParentescoBeneficiario") val parentescoBeneficiario: Int,
    @JsonProperty("GeneroBeneficiario") val generoBeneficiario: String,
    @JsonProperty("PorcentajeBeneficiario") val porcentajeBeneficiario: Int
)

@JsonInclude(JsonInclude.Include.NON_NULL)
data class CuestionariosAsegurado(
    @JsonProperty("CuestionarioMedico") val cuestionarioMedico: CuestionarioMedico? = null,
    @JsonProperty("Cuestionario492Aseg") val cuestionario492Aseg: Cuestionario492? = null // --- NUEVO: 492 DE ASEGURADO ---
)

@JsonInclude(JsonInclude.Include.NON_NULL)
data class CuestionarioMedico(
    @JsonProperty("@ResultadoMed") val resultadoMed: String,
    @JsonProperty("Pregunta") val preguntas: List<PreguntaMed>
)

@JsonInclude(JsonInclude.Include.NON_NULL)
data class PreguntaMed(
    @JsonProperty("NoPreguntaMed") val noPreguntaMed: Int,
    @JsonProperty("RespuestaMed") val respuestaMed: String
)

@JsonInclude(JsonInclude.Include.NON_NULL)
data class CoberturasWrapper(@JsonProperty("Cobertura") val coberturaList: List<Cobertura>)

@JsonInclude(JsonInclude.Include.NON_NULL)
data class Cobertura(
    @JsonProperty("@MarcaAmparada") val marcaAmparada: String,
    @JsonProperty("@NoCobertura") val noCobertura: String,
    @JsonProperty("ConceptoCB") val conceptoCBList: List<ConceptoCB>? = null
)

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ConceptoCB(
    @JsonProperty("@NomConceptoCB") val nomConceptoCB: String,
    @JsonProperty("ValConceptoCB") val valConceptoCB: Double,
    @JsonProperty("UniConceptoCB") val uniConceptoCB: String,
    @JsonProperty("MonConceptoMNCB") val monConceptoMNCB: Double
)

@JsonInclude(JsonInclude.Include.NON_NULL)
data class Cuestionario492(@JsonProperty("Pregunta") val preguntas: List<Pregunta492>)

@JsonInclude(JsonInclude.Include.NON_NULL)
data class Pregunta492(
    @JsonProperty("NoPreguntaCon") val noPreguntaCon: Int,
    @JsonProperty("RespuestaCon") val respuestaCon: Any
)

//  MODELOS DE CATÁLOGO
@JsonIgnoreProperties(ignoreUnknown = true)
data class CodPosResponse(
    val data: CodPosData? = null
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class CodPosData(
    val estado: CodPosLocation? = null,
    val alc_mcpio: CodPosLocation? = null,
    val colonias: List<CodPosLocation>? = null
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class CodPosLocation(
    val id: String? = null,
    val descripcion: String? = null
)

data class PreguntaCatalogo(
    val id: Int? = null,
    val codigo_pregunta: String? = null,
    val pregunta: String? = null,
    val respuesta: List<Any>? = null,
    val opciones: Any? = null
)

//  REQUEST SIMPLIFICADO
data class CotizacionSimplificadaRequest(
    @Schema(example = "2", description = "2=Cotizar, 7=Solicitud, 3=Emitir") val tipoMovimiento: String,
    val folioCotizacion: String? = null,
    val folioSolicitud: String? = null,
    val cp: String,
    val idColonia: String,
    val rfc: String,
    val nombre: String,
    val apellidoPaterno: String,
    val apellidoMaterno: String,
    val fechaNacimiento: String,
    val genero: String,
    val calle: String,
    val noExterior: String,
    val email: String,
    val celular: Long,
    val ocupacion: Int,
    val formaPago: String, // "A", "M", "S", "T"
    val origenRecursos: Int, // 1 al 7
    val emailFactura: String? = null,
    val cpFactura: String? = null,
    val asegurados: List<AseguradoSimplificado>,
    val respuestas492: List<RespuestaSimplificada>? = null
)

data class AseguradoSimplificado(
    val nombre: String,
    val apellidoPaterno: String,
    val apellidoMaterno: String,
    val fecha_nacimiento: String,
    val sexo: String,
    val rfc: String,
    val parentesco: Int,
    val coberturas: List<CoberturaSeleccionada>,
    val beneficiarios: List<BeneficiarioSimplificado>? = null,
    val respuestasMedicas: List<RespuestaSimplificada>? = null,
    val respuestas492Aseg: List<RespuestaSimplificada>? = null
)

data class CoberturaSeleccionada(
    val noCobertura: String,
    val sumaAsegurada: Double
)

data class BeneficiarioSimplificado(
    val nombre: String,
    val apellidoPaterno: String,
    val apellidoMaterno: String,
    val fechaNacimiento: String,
    val genero: String,
    val parentesco: Int,
    val porcentaje: Int
)

data class RespuestaSimplificada(val id_pregunta: Int, val respuesta: String)

// --- DOCUMENTOS, TOKEN Y FIRMAS ---
data class FormatPrintingRequest(val solicitud: String? = null)
data class FormatPrintingResponse(val base64: String? = null)
data class TokenRequest(val application: String, val contact: String, val modeOtp: String)
data class VerifyTokenRequest(val application: String, val pin: String)
data class CfdiRequest(val name: String, val document: String)
data class SignatureRequest(val grafo: Grafo, val grafo2: Grafo? = null, val name: String, val solicitud: String, val numtrabajo: String) {
    data class Grafo(val time_simple: List<String>?, val clickX_simple: List<Double>?, val clickY_simple: List<Double>?, val pressure: String?, val fileContent: String?)
}