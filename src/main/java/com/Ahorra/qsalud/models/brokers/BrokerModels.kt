package com.Ahorra.qsalud.models.brokers

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

@JsonInclude(JsonInclude.Include.NON_NULL)
data class MovimientosRequest(
    @JsonProperty("Movimientos") val movimientos: List<MovimientoWrapper>?
)

@JsonInclude(JsonInclude.Include.NON_NULL)
data class MovimientoWrapper(
    @JsonProperty("Movimiento") val detalle: Movimiento?
)

@JsonInclude(JsonInclude.Include.NON_NULL)
data class Movimiento(
    @JsonProperty("@TipoMovimiento") val tipoMovimiento: String?,
    @JsonProperty("@NoNegocio") val noNegocio: String?,
    @JsonProperty("@Usuario") val usuario: String?,
    @JsonProperty("@NoCotizacion") val noCotizacion: String?,
    @JsonProperty("@Solicitud") val solicitud: String?,
    @JsonProperty("@SubRamo") val subRamo: String?,
    @JsonProperty("@NoEndoso") val noEndoso: String?,
    @JsonProperty("@NoPoliza") val noPoliza: String?,
    @JsonProperty("@NoOTra") val noOTra: String?,

    @JsonProperty("DatosGenerales") val datosGenerales: DatosGenerales?,
    @JsonProperty("SubGrupos") val subGrupos: SubGruposWrapper?,
    @JsonProperty("Contratante") val contratante: Contratante?,
    @JsonProperty("Asegurados") val asegurados: AseguradosWrapper?,
    @JsonProperty("Planes") val planes: PlanesWrapper?,
    @JsonProperty("Recibos") val recibos: RecibosWrapper?,
    @JsonProperty("Prima") val prima: Prima?,

    @JsonProperty("CodigoError") val codigoError: String?,
    @JsonProperty("MensajeError") val mensajeError: String?,
    @JsonProperty("MensajeUsuario") val mensajeUsuario: String?
)

// --- DATOS GENERALES ---
@JsonInclude(JsonInclude.Include.NON_NULL)
data class DatosGenerales(
    @JsonProperty("FechaInicio") val fechaInicio: String?,
    @JsonProperty("FechaTermino") val fechaTermino: String?,
    @JsonProperty("FechaIngreso") val fechaIngreso: String?,
    @JsonProperty("Antiguedad") val antiguedad: String?,
    @JsonProperty("FechaEmision") val fechaEmision: String?,
    @JsonProperty("TipoTramite") val tipoTramite: String?,
    @JsonProperty("EstadoSolicitud") val estadoSolicitud: String?,
    @JsonProperty("Suscripcion") val suscripcion: String?,
    @JsonProperty("Moneda") val moneda: String?,
    @JsonProperty("Agente") val agente: String?,
    @JsonProperty("Promotor") val promotor: String?,
    @JsonProperty("FormaPago") val formaPago: String?,
    @JsonProperty("CodigoOficina") val codigoOficina: String?,
    @JsonProperty("CPIVA") val cpIva: String?,
    @JsonProperty("CodigoProducto") val codigoProducto: String?,
    @JsonProperty("NivelProducto") val nivelProducto: String?,
    @JsonProperty("Tarifa") val tarifa: String?,
    @JsonProperty("TipoPoliza") val tipoPoliza: String?,
    @JsonProperty("Compensacion") val compensacion: String?,
    @JsonProperty("PeriodoGracia") val periodoGracia: String?,
    @JsonProperty("OrigenRecursos") val origenRecursos: String?,
    @JsonProperty("AutorizacionInfoMedica") val autorizacionInfoMedica: String?,
    @JsonProperty("DocumentacionCorreo") val documentacionCorreo: String?
)

// --- SUBGRUPOS ---
@JsonInclude(JsonInclude.Include.NON_NULL)
data class SubGruposWrapper(
    @JsonProperty("SubGrupo") val subGrupoList: List<SubGrupo>?
)

@JsonInclude(JsonInclude.Include.NON_NULL)
data class SubGrupo(
    @JsonProperty("@NoSubGrupPol") val noSubGrupPol: String?,
    @JsonProperty("ConceptoSG") val conceptoSGList: List<ConceptoSG>?
)

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ConceptoSG(
    @JsonProperty("@NomConceptoSG") val nomConceptoSG: String?,
    @JsonProperty("ValConceptoSG") val valConceptoSG: Any?,
    @JsonProperty("UniConceptoSG") val uniConceptoSG: String?,
    @JsonProperty("MonConceptoMNSG") val monConceptoMNSG: Double?
)

// --- CONTRATANTE Y CUESTIONARIOS ---
@JsonInclude(JsonInclude.Include.NON_NULL)
data class Contratante(
    @JsonProperty("@NoContratante") val noContratante: String?,
    @JsonProperty("@TipoPersona") val tipoPersona: String?,
    @JsonProperty("@NacionalidadContra") val nacionalidadContra: String?,
    @JsonProperty("@RFCContra") val rfcContra: String?,
    @JsonProperty("NombreContratante") val nombreContratante: String?,
    @JsonProperty("APContratante") val apContratante: String?,
    @JsonProperty("AMContratante") val amContratante: String?,
    @JsonProperty("FechaNacimientoContratante") val fechaNacimientoContratante: String?,
    @JsonProperty("GeneroContratante") val generoContratante: String?,
    @JsonProperty("NumeroUnicoContratante") val numeroUnicoContratante: String?,
    @JsonProperty("CalleContratante") val calleContratante: String?,
    @JsonProperty("EntreCallesContra") val entreCallesContra: String?,
    @JsonProperty("ColoniaContratante") val coloniaContratante: String?,
    @JsonProperty("MunicipioContratante") val municipioContratante: String?,
    @JsonProperty("EstadoContratante") val estadoContratante: String?,
    @JsonProperty("CPContratante") val cpContratante: String?,
    @JsonProperty("PaisNacimientoContra") val paisNacimientoContra: String?,
    @JsonProperty("EntidadFedNacimientoContra") val entidadFedNacimientoContra: String?,
    @JsonProperty("TipoDomicilioContra") val tipoDomicilioContra: String?,
    @JsonProperty("NoExteriorContra") val noExteriorContra: String?,
    @JsonProperty("NoInteriorContra") val noInteriorContra: String?,
    @JsonProperty("TelefonoContratante") val telefonoContratante: String?,
    @JsonProperty("CURPContratante") val curpContratante: String?,
    @JsonProperty("CelularContratante") val celularContratante: String?,
    @JsonProperty("eMailContratante") val emailContratante: String?,
    @JsonProperty("OcupacionContratante") val ocupacionContratante: String?,
    @JsonProperty("eMailFactura") val emailFactura: String?,
    @JsonProperty("CPFactura") val cpFactura: String?,
    @JsonProperty("ComposicionAccionaria") val composicionAccionaria: Any?,
    @JsonProperty("Cuestionario492") val cuestionario492: Cuestionario492?
)

// --- ASEGURADOS Y SUS DETALLES ---
@JsonInclude(JsonInclude.Include.NON_NULL)
data class AseguradosWrapper(
    @JsonProperty("Asegurado") val aseguradoList: List<Asegurado>?
)

@JsonInclude(JsonInclude.Include.NON_NULL)
data class Asegurado(
    @JsonProperty("@Certificado") val certificado: String?,
    @JsonProperty("@NoAsegurado") val noAsegurado: String?,
    @JsonProperty("@NoInciso") val noInciso: String?,
    @JsonProperty("@NoSubGrupAseg") val noSubGrupAseg: String?,
    @JsonProperty("@Parentesco") val parentesco: String?,
    @JsonProperty("@TipoAsegurado") val tipoAsegurado: String?,
    @JsonProperty("DatosAsegurado") val datosAsegurado: DatosAsegurado?,
    @JsonProperty("Beneficiarios") val beneficiarios: BeneficiariosWrapper?,
    @JsonProperty("Cuestionarios") val cuestionarios: CuestionariosAsegurado?,
    @JsonProperty("Coberturas") val coberturas: CoberturasWrapper?
)

@JsonInclude(JsonInclude.Include.NON_NULL)
data class DatosAsegurado(
    @JsonProperty("NombreAsegurado") val nombreAsegurado: String?,
    @JsonProperty("ApellidoPatAsegurado") val apellidoPatAsegurado: String?,
    @JsonProperty("ApellidoMatAsegurado") val apellidoMatAsegurado: String?,
    @JsonProperty("Nacional") val nacional: String?,
    @JsonProperty("Genero") val genero: String?,
    @JsonProperty("FechaNacimiento") val fechaNacimiento: String?,
    @JsonProperty("RFCAsegurado") val rfcAsegurado: String?,
    @JsonProperty("CURPAsegurado") val curpAsegurado: String?,
    @JsonProperty("NoUnico") val noUnico: String?,
    @JsonProperty("TelefonoAsegurado") val telefonoAsegurado: String?,
    @JsonProperty("Celular") val celular: String?,
    @JsonProperty("CelularAsegurado") val celularAsegurado: String?,
    @JsonProperty("eMail") val email: String?,
    @JsonProperty("PaisNacimientoAsegurado") val paisNacimientoAsegurado: String?,
    @JsonProperty("OcupacionAsegurado") val ocupacionAsegurado: String?,
    @JsonProperty("EstadoCivilAsegurado") val estadoCivilAsegurado: String?,
    @JsonProperty("NoEmpleado") val noEmpleado: String?
)

// --- BENEFICIARIOS ---
@JsonInclude(JsonInclude.Include.NON_NULL)
data class BeneficiariosWrapper(
    @JsonProperty("Beneficiario") val beneficiarioList: List<Beneficiario>?
)

@JsonInclude(JsonInclude.Include.NON_NULL)
data class Beneficiario(
    @JsonProperty("@NoBeneficiario") val noBeneficiario: String?,
    @JsonProperty("NombreBeneficiario") val nombreBeneficiario: String?,
    @JsonProperty("ApellidoPatBeneficiario") val apellidoPatBeneficiario: String?,
    @JsonProperty("ApellidoMatBeneficiario") val apellidoMatBeneficiario: String?,
    @JsonProperty("FechaNacimientoBeneficiario") val fechaNacimientoBeneficiario: String?,
    @JsonProperty("DireccionBeneficiario") val direccionBeneficiario: String?,
    @JsonProperty("ParentescoBeneficiario") val parentescoBeneficiario: String?,
    @JsonProperty("GeneroBeneficiario") val generoBeneficiario: String?,
    @JsonProperty("PorcentajeBeneficiario") val porcentajeBeneficiario: String?
)

// --- CUESTIONARIOS ---
@JsonInclude(JsonInclude.Include.NON_NULL)
data class CuestionariosAsegurado(
    @JsonProperty("CuestionarioMedico") val cuestionarioMedico: CuestionarioMedico?,
    @JsonProperty("Cuestionario492Aseg") val cuestionario492Aseg: Cuestionario492?
)

@JsonInclude(JsonInclude.Include.NON_NULL)
data class CuestionarioMedico(
    @JsonProperty("@ResultadoMed") val resultadoMed: String?,
    @JsonProperty("Pregunta") val preguntas: List<PreguntaMed>?
)

@JsonInclude(JsonInclude.Include.NON_NULL)
data class PreguntaMed(
    @JsonProperty("NoPreguntaMed") val noPreguntaMed: String?,
    @JsonProperty("RespuestaMed") val respuestaMed: String?,
    @JsonProperty("DiagnosticoMed") val diagnosticoMed: String?
)

@JsonInclude(JsonInclude.Include.NON_NULL)
data class Cuestionario492(
    @JsonProperty("@GradoRiesgo") val gradoRiesgo: String?,
    @JsonProperty("Pregunta") val preguntas: List<Pregunta492>?
)

@JsonInclude(JsonInclude.Include.NON_NULL)
data class Pregunta492(
    @JsonProperty("NoPreguntaCon") val noPreguntaCon: String?,
    @JsonProperty("NoPregunta492") val noPregunta492: String?,
    @JsonProperty("RespuestaCon") val respuestaCon: Any?,
    @JsonProperty("Respuesta492") val respuesta492: Any?
)

// --- COBERTURAS ---
@JsonInclude(JsonInclude.Include.NON_NULL)
data class CoberturasWrapper(
    @JsonProperty("Cobertura") val coberturaList: List<Cobertura>?
)

@JsonInclude(JsonInclude.Include.NON_NULL)
data class Cobertura(
    @JsonProperty("@MarcaAmparada") val marcaAmparada: String?,
    @JsonProperty("@NoCobertura") val noCobertura: String?,
    @JsonProperty("@NoServicios") val noServicios: String?,
    @JsonProperty("@NoSubcoberturas") val noSubcoberturas: String?,
    @JsonProperty("@PrimaCoberturaMN") val primaCoberturaMN: String?,
    @JsonProperty("ConceptoCB") val conceptoCBList: List<ConceptoCB>?,
    @JsonProperty("SubCoberturas") val subCoberturas: SubCoberturasWrapper?
)

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ConceptoCB(
    @JsonProperty("@NomConceptoCB") val nomConceptoCB: String?,
    @JsonProperty("ValConceptoCB") val valConceptoCB: Any?,
    @JsonProperty("UniConceptoCB") val uniConceptoCB: String?,
    @JsonProperty("MonConceptoMNCB") val monConceptoMNCB: Double?
)

@JsonInclude(JsonInclude.Include.NON_NULL)
data class SubCoberturasWrapper(
    @JsonProperty("SubCobertura") val subCoberturaList: List<SubCobertura>?
)

@JsonInclude(JsonInclude.Include.NON_NULL)
data class SubCobertura(
    @JsonProperty("@NoSubCobertura") val noSubCobertura: String?,
    @JsonProperty("@NomSubCobertura") val nomSubCoberturaAtr: String?,
    @JsonProperty("NomSubCobertura") val nomSubCoberturaObj: String?,
    @JsonProperty("ConceptoSC") val conceptoSCList: List<ConceptoSC>?
)

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ConceptoSC(
    @JsonProperty("@NomConceptoSC") val nomConceptoSC: String?,
    @JsonProperty("ValConceptoSC") val valConceptoSC: Any?,
    @JsonProperty("UniConceptoSC") val uniConceptoSC: String?,
    @JsonProperty("MonConceptoMNSC") val monConceptoMNSC: Double?
)

// --- PLANES Y PAGOS ---
@JsonInclude(JsonInclude.Include.NON_NULL)
data class PlanesWrapper(
    @JsonProperty("Plan") val planList: List<Plan>?
)

@JsonInclude(JsonInclude.Include.NON_NULL)
data class Plan(
    @JsonProperty("@NomPlan") val nomPlan: String?,
    @JsonProperty("@PrimaPlanPago") val primaPlanPago: String?,
    @JsonProperty("FechaIniPago") val fechaIniPago: String?,
    @JsonProperty("PrimaNetaPago") val primaNetaPago: Double?,
    @JsonProperty("RecargoFinancieroPago") val recargoFinancieroPago: Double?,
    @JsonProperty("PrimaTotalPago") val primaTotalPago: Double?,
    @JsonProperty("PagoInicial") val pagoInicial: DetallePago?,
    @JsonProperty("PagosSubsecuentes") val pagosSubsecuentes: DetallePago?
)

@JsonInclude(JsonInclude.Include.NON_NULL)
data class DetallePago(
    @JsonProperty("FechaIniPago") val fechaIniPago: String?,
    @JsonProperty("PrimaNetaPago") val primaNetaPago: Double?,
    @JsonProperty("RecargoFinancieroPago") val recargoFinancieroPago: Double?,
    @JsonProperty("PrimaTotalPago") val primaTotalPago: Double?
)

// --- RECIBOS Y PRIMAS ---
@JsonInclude(JsonInclude.Include.NON_NULL)
data class RecibosWrapper(
    @JsonProperty("Recibo") val recibo: Recibo?
)

@JsonInclude(JsonInclude.Include.NON_NULL)
data class Recibo(
    @JsonProperty("@NoRecibo") val noRecibo: String?,
    @JsonProperty("FechaIniRecibo") val fechaIniRecibo: String?,
    @JsonProperty("PrimaNetaRecibo") val primaNetaRecibo: Double?,
    @JsonProperty("DerechoRecibo") val derechoRecibo: Double?,
    @JsonProperty("RecargoRecibo") val recargoRecibo: Double?,
    @JsonProperty("ImpuestoRecibo") val impuestoRecibo: Double?,
    @JsonProperty("PrimaTotalRecibo") val primaTotalRecibo: Double?,
    @JsonProperty("ComisionRecibo") val comisionRecibo: Double?
)

@JsonInclude(JsonInclude.Include.NON_NULL)
data class Prima(
    @JsonProperty("PrimaNetaPoliza") val primaNetaPoliza: Double?,
    @JsonProperty("RecargoPoliza") val recargoPoliza: Double?,
    @JsonProperty("DerechoPoliza") val derechoPoliza: Double?,
    @JsonProperty("SubTotalPoliza") val subTotalPoliza: Double?,
    @JsonProperty("ImpuestoPoliza") val impuestoPoliza: Double?,
    @JsonProperty("PrimaTotalPoliza") val primaTotalPoliza: Double?,
    @JsonProperty("BonificacionCN") val bonificacionCN: Double?,
    @JsonProperty("BonificacionCRF") val bonificacionCRF: Double?,
    @JsonProperty("PorcentajeDescuentoPeriodoGracia") val porcentajeDescuentoPeriodoGracia: Double?,
    @JsonProperty("SumAsegTotalPolizaMN") val sumAsegTotalPolizaMN: Double?
)