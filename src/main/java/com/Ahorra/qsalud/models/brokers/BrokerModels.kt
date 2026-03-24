package com.Ahorra.qsalud.models.brokers

import com.fasterxml.jackson.annotation.JsonProperty

data class MovimientosRequest(
    @JsonProperty("Movimientos") val movimientos: List<MovimientoWrapper>?
)

data class MovimientoWrapper(
    @JsonProperty("Movimiento") val detalle: Movimiento?
)

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
data class DatosGenerales(
    @JsonProperty("FechaInicio") val fechaInicio: String?,
    @JsonProperty("FechaTermino") val fechaTermino: String?,
    @JsonProperty("FechaIngreso") val fechaIngreso: String?,
    @JsonProperty("Antiguedad") val antiguedad: String?,
    @JsonProperty("FechaEmision") val fechaEmision: String?,
    @JsonProperty("TipoTramite") val tipoTramite: String?,
    @JsonProperty("EstadoSolicitud") val estadoSolicitud: Int?,
    @JsonProperty("Suscripcion") val suscripcion: String?,
    @JsonProperty("Moneda") val moneda: Int?,
    @JsonProperty("Agente") val agente: Int?,
    @JsonProperty("Promotor") val promotor: Int?,
    @JsonProperty("FormaPago") val formaPago: String?,
    @JsonProperty("CodigoOficina") val codigoOficina: Int?,
    @JsonProperty("CPIVA") val cpIva: String?,
    @JsonProperty("CodigoProducto") val codigoProducto: Long?,
    @JsonProperty("NivelProducto") val nivelProducto: Int?,
    @JsonProperty("Tarifa") val tarifa: Int?,
    @JsonProperty("TipoPoliza") val tipoPoliza: String?,
    @JsonProperty("Compensacion") val compensacion: Int?,
    @JsonProperty("PeriodoGracia") val periodoGracia: Int?,
    @JsonProperty("OrigenRecursos") val origenRecursos: Int?,
    @JsonProperty("AutorizacionInfoMedica") val autorizacionInfoMedica: String?,
    @JsonProperty("DocumentacionCorreo") val documentacionCorreo: String?
)

// --- SUBGRUPOS ---
data class SubGruposWrapper(
    @JsonProperty("SubGrupo") val subGrupoList: List<SubGrupo>?
)

data class SubGrupo(
    @JsonProperty("@NoSubGrupPol") val noSubGrupPol: String?,
    @JsonProperty("ConceptoSG") val conceptoSGList: List<ConceptoSG>?
)

data class ConceptoSG(
    @JsonProperty("@NomConceptoSG") val nomConceptoSG: String?,
    @JsonProperty("ValConceptoSG") val valConceptoSG: Any?,
    @JsonProperty("UniConceptoSG") val uniConceptoSG: String?,
    @JsonProperty("MonConceptoMNSG") val monConceptoMNSG: Double?
)

// --- CONTRATANTE Y CUESTIONARIOS ---
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
    @JsonProperty("MunicipioContratante") val municipioContratante: Int?,
    @JsonProperty("EstadoContratante") val estadoContratante: Int?,
    @JsonProperty("CPContratante") val cpContratante: String?,
    @JsonProperty("PaisNacimientoContra") val paisNacimientoContra: Int?,
    @JsonProperty("EntidadFedNacimientoContra") val entidadFedNacimientoContra: Int?,
    @JsonProperty("TipoDomicilioContra") val tipoDomicilioContra: Int?,
    @JsonProperty("NoExteriorContra") val noExteriorContra: String?,
    @JsonProperty("NoInteriorContra") val noInteriorContra: String?,
    @JsonProperty("TelefonoContratante") val telefonoContratante: Long?,
    @JsonProperty("CURPContratante") val curpContratante: String?,
    @JsonProperty("CelularContratante") val celularContratante: Long?,
    @JsonProperty("eMailContratante") val eMailContratante: String?,
    @JsonProperty("OcupacionContratante") val ocupacionContratante: Int?,
    @JsonProperty("eMailFactura") val eMailFactura: String?,
    @JsonProperty("CPFactura") val cpFactura: String?,
    @JsonProperty("ComposicionAccionaria") val composicionAccionaria: Any?,
    @JsonProperty("Cuestionario492") val cuestionario492: Cuestionario492?
)

// --- ASEGURADOS Y SUS DETALLES ---
data class AseguradosWrapper(
    @JsonProperty("Asegurado") val aseguradoList: List<Asegurado>?
)

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
    @JsonProperty("TelefonoAsegurado") val telefonoAsegurado: Long?,
    @JsonProperty("Celular") val celular: Long?,
    @JsonProperty("CelularAsegurado") val celularAsegurado: Long?,
    @JsonProperty("eMail") val eMail: String?,
    @JsonProperty("PaisNacimientoAsegurado") val paisNacimientoAsegurado: Int?,
    @JsonProperty("OcupacionAsegurado") val ocupacionAsegurado: Int?,
    @JsonProperty("EstadoCivilAsegurado") val estadoCivilAsegurado: String?,
    @JsonProperty("NoEmpleado") val noEmpleado: Int?
)

// --- BENEFICIARIOS ---
data class BeneficiariosWrapper(
    @JsonProperty("Beneficiario") val beneficiarioList: List<Beneficiario>?
)

data class Beneficiario(
    @JsonProperty("@NoBeneficiario") val noBeneficiario: String?,
    @JsonProperty("NombreBeneficiario") val nombreBeneficiario: String?,
    @JsonProperty("ApellidoPatBeneficiario") val apellidoPatBeneficiario: String?,
    @JsonProperty("ApellidoMatBeneficiario") val apellidoMatBeneficiario: String?,
    @JsonProperty("FechaNacimientoBeneficiario") val fechaNacimientoBeneficiario: String?,
    @JsonProperty("DireccionBeneficiario") val direccionBeneficiario: String?,
    @JsonProperty("ParentescoBeneficiario") val parentescoBeneficiario: Int?,
    @JsonProperty("GeneroBeneficiario") val generoBeneficiario: String?,
    @JsonProperty("PorcentajeBeneficiario") val porcentajeBeneficiario: Int?
)

// --- CUESTIONARIOS ---
data class CuestionariosAsegurado(
    @JsonProperty("CuestionarioMedico") val cuestionarioMedico: CuestionarioMedico?,
    @JsonProperty("Cuestionario492Aseg") val cuestionario492Aseg: Cuestionario492?
)

data class CuestionarioMedico(
    @JsonProperty("@ResultadoMed") val resultadoMed: String?,
    @JsonProperty("Pregunta") val preguntas: List<PreguntaMed>?
)

data class PreguntaMed(
    @JsonProperty("NoPreguntaMed") val noPreguntaMed: Int?,
    @JsonProperty("RespuestaMed") val respuestaMed: String?,
    @JsonProperty("DiagnosticoMed") val diagnosticoMed: String?
)

data class Cuestionario492(
    @JsonProperty("@GradoRiesgo") val gradoRiesgo: String?,
    @JsonProperty("Pregunta") val preguntas: List<Pregunta492>?
)

data class Pregunta492(
    @JsonProperty("NoPreguntaCon") val noPreguntaCon: Int?,
    @JsonProperty("NoPregunta492") val noPregunta492: Int?,
    @JsonProperty("RespuestaCon") val respuestaCon: Any?,
    @JsonProperty("Respuesta492") val respuesta492: Any?
)

// --- COBERTURAS ---
data class CoberturasWrapper(
    @JsonProperty("Cobertura") val coberturaList: List<Cobertura>?
)

data class Cobertura(
    @JsonProperty("@MarcaAmparada") val marcaAmparada: String?,
    @JsonProperty("@NoCobertura") val noCobertura: String?,
    @JsonProperty("@NoServicios") val noServicios: String?,
    @JsonProperty("@NoSubcoberturas") val noSubcoberturas: String?,
    @JsonProperty("@PrimaCoberturaMN") val primaCoberturaMN: String?,
    @JsonProperty("ConceptoCB") val conceptoCBList: List<ConceptoCB>?,
    @JsonProperty("SubCoberturas") val subCoberturas: SubCoberturasWrapper?
)

data class ConceptoCB(
    @JsonProperty("@NomConceptoCB") val nomConceptoCB: String?,
    @JsonProperty("ValConceptoCB") val valConceptoCB: Any?, // Double o String (Ej. "INCLUIDO")
    @JsonProperty("UniConceptoCB") val uniConceptoCB: String?,
    @JsonProperty("MonConceptoMNCB") val monConceptoMNCB: Double?
)

data class SubCoberturasWrapper(
    @JsonProperty("SubCobertura") val subCoberturaList: List<SubCobertura>?
)

data class SubCobertura(
    @JsonProperty("@NoSubCobertura") val noSubCobertura: String?,
    @JsonProperty("@NomSubCobertura") val nomSubCoberturaAtr: String?,
    @JsonProperty("NomSubCobertura") val nomSubCoberturaObj: String?,
    @JsonProperty("ConceptoSC") val conceptoSCList: List<ConceptoSC>?
)

data class ConceptoSC(
    @JsonProperty("@NomConceptoSC") val nomConceptoSC: String?,
    @JsonProperty("ValConceptoSC") val valConceptoSC: Any?,
    @JsonProperty("UniConceptoSC") val uniConceptoSC: String?,
    @JsonProperty("MonConceptoMNSC") val monConceptoMNSC: Double?
)

// --- PLANES Y PAGOS ---
data class PlanesWrapper(
    @JsonProperty("Plan") val planList: List<Plan>?
)

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

data class DetallePago(
    @JsonProperty("FechaIniPago") val fechaIniPago: String?,
    @JsonProperty("PrimaNetaPago") val primaNetaPago: Double?,
    @JsonProperty("RecargoFinancieroPago") val recargoFinancieroPago: Double?,
    @JsonProperty("PrimaTotalPago") val primaTotalPago: Double?
)

// --- RECIBOS Y PRIMAS ---
data class RecibosWrapper(
    @JsonProperty("Recibo") val recibo: Recibo?
)

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