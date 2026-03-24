package com.Ahorra.qsalud.models.pagos

import com.fasterxml.jackson.annotation.JsonProperty

// Petición para generar Link de Pago
data class GenLinkRequest(
    val poliza: String, // 10 o 12 dígitos
    val email: String?,
    val telefono: String?, // Opcional
    val domi: String, // "1" domiciliación, "0" pago
    val endoso: String? = "000000"
)

data class GenLinkResponse(
    val code: Int?,
    val message: String?,
    val date: String?,
    val body: GenLinkBody?
) {
    data class GenLinkBody(
        val vigencia: String?,
        val estatus: Int?,
        val endoso: String?,
        val fechaHora: String?,
        val parcialidad: String?,
        val mensaje: String?,
        val poliza: String?,
        val email: String?,
        val titular: String?
    )
}

// Petición para consultar domiciliación (ConsulDom)
data class ConsulDomRequest(
    val poliza: String, // 10 dígitos + año
    val akey: String // 5 dígitos
)

data class ConsulDomResponse(
    val code: Int?,
    val message: String?,
    val date: String?,
    val body: ConsulDomBody?
) {
    data class ConsulDomBody(
        val estatus: Int?,
        val anio: String?,
        val poliza: String?,
        val ramo: String?,
        val akey: String?,
        val titular: String?,
        val banco: String?,
        @JsonProperty("ultimos Digitos") val ultimosDigitos: String?,
        val Mensaje: String?
    )
}


data class ValidaBinRequest(val validaBin: String) // 6 dígitos
data class PolizaRequest(val poliza: String) // Usado para searchReceipts, searchLink, fareceipt
data class CancelLinkRequest(val poliza: String, val email: String?)
data class GenWebPayRequest(val poliza: String, val email: String, val usuccess: String, val ufail: String)