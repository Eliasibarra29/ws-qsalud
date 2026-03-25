package com.Ahorra.qsalud.models.brokers

// Petición para descargar formatos (Cotización, Emisión, Solicitud)
data class FormatPrintingRequest(
    val cotizacion: String? = null, // 8 dígitos
    val emision: String? = null, // 18 dígitos
    val solicitud: String? = null // 12 dígitos
)

data class FormatPrintingResponse(
    val base64: String? // Cadena en base64 (.pdf o .zip)
)

// Generación y Validación de Token / PIN OTP
data class TokenRequest(
    val application: String, // solicitud a 12 dígitos
    val contact: String, // teléfono a 10 dígitos / correo
    val modeOtp: String // "01" o "02"
)

data class VerifyTokenRequest(
    val application: String, // solicitud a 12 digitos
    val pin: String // PIN a 6 dígitos
)

// Petición para Validación CFDI
data class CfdiRequest(
    val name: String, // nombre
    val document: String // b64document
)

// Petición para Guardado de Firma y Biométricos
data class SignatureRequest(
    val grafo: Grafo,
val grafo2: Grafo? = null, //para el asegurado titular
val name: String, // nombre contratante
val name2: String? = null, // Opcional
val solicitud: String, // 12 digitos
val numtrabajo: String // 10 digitos
) {
    data class Grafo(
        val time_simple: List<String>?,
    val clickX_simple: List<Double>?,
    val clickY_simple: List<Double>?,
    val pressure: String?,
    val fileContent: String? // b64 firma png 400x200 px
    )
}