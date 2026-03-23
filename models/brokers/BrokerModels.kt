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
    @JsonProperty("DatosGenerales") val datosGenerales: DatosGenerales?,
    @JsonProperty("Contratante") val contratante: Contratante?,
    @JsonProperty("Asegurados") val asegurados: AseguradosWrapper?
)

data class DatosGenerales(
    @JsonProperty("FechaInicio") val fechaInicio: String?,
    @JsonProperty("FechaTermino") val fechaTermino: String?,
    @JsonProperty("Moneda") val moneda: Int?,
    @JsonProperty("Agente") val agente: Int?,
    @JsonProperty("FormaPago") val formaPago: String?,
    @JsonProperty("CodigoOficina") val codigoOficina: Int?,
    @JsonProperty("CodigoProducto") val codigoProducto: Long?,
    @JsonProperty("NivelProducto") val nivelProducto: Int?,
    @JsonProperty("CPIVA") val cpIva: String?,
    @JsonProperty("PeriodoGracia") val periodoGracia: Int?
)

data class AseguradosWrapper(
    @JsonProperty("Asegurado") val aseguradoList: List<Asegurado>?
)

data class Asegurado(
    @JsonProperty("@Certificado") val certificado: String?,
    @JsonProperty("@TipoAsegurado") val tipoAsegurado: String?,
    @JsonProperty("@Parentesco") val parentesco: String?,
    @JsonProperty("@NoAsegurado") val noAsegurado: String?,
    @JsonProperty("@NoInciso") val noInciso: String?,
    @JsonProperty("DatosAsegurado") val datosAsegurado: DatosAsegurado?,
    @JsonProperty("Coberturas") val coberturas: CoberturasWrapper?
)

data class DatosAsegurado(
    @JsonProperty("NombreAsegurado") val nombreAsegurado: String?,
    @JsonProperty("ApellidoPatAsegurado") val apellidoPatAsegurado: String?,
    @JsonProperty("ApellidoMatAsegurado") val apellidoMatAsegurado: String?,
    @JsonProperty("Genero") val genero: String?,
    @JsonProperty("FechaNacimiento") val fechaNacimiento: String?
)

data class CoberturasWrapper(
    @JsonProperty("Cobertura") val coberturaList: List<Cobertura>?
)

data class Cobertura(
    @JsonProperty("@MarcaAmparada") val marcaAmparada: String?,
    @JsonProperty("@NoCobertura") val noCobertura: String?,
    @JsonProperty("ConceptoCB") val conceptoCBList: List<ConceptoCB>?
)

data class ConceptoCB(
    @JsonProperty("@NomConceptoCB") val nomConceptoCB: String?,
    @JsonProperty("ValConceptoCB") val valConceptoCB: Double?,
    @JsonProperty("UniConceptoCB") val uniConceptoCB: String?,
    @JsonProperty("MonConceptoMNCB") val monConceptoMNCB: Double?
)

data class Contratante(
    @JsonProperty("@TipoPersona") val tipoPersona: String?,
    @JsonProperty("@RFCContra") val rfcContra: String?,
    @JsonProperty("NombreContratante") val nombreContratante: String?
    // Agrega aquí más campos si los necesitas en el futuro
)