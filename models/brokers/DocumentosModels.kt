package com.Ahorra.qsalud.models.brokers

// Petición para descargar formatos (Cotización, Emisión, Solicitud)
data class FormatPrintingRequest(
    val cotizacion: String? = null, // 8 dígitos [cite: 751, 755]
    val emision: String? = null, // 18 dígitos [cite: 784, 787]
    val solicitud: String? = null // 12 dígitos [cite: 817, 820]
)

data class FormatPrintingResponse(
    val base64: String? // Cadena en base64 (.pdf o .zip) [cite: 774, 805, 839]
)

// Generación y Validación de Token / PIN OTP
data class TokenRequest(
    val application: String, // solicitud a 12 dígitos [cite: 1021]
    val contact: String, // teléfono a 10 dígitos / correo [cite: 1022]
    val modeOtp: String // "01" o "02" [cite: 1023]
)

data class VerifyTokenRequest(
    val application: String, // solicitud a 12 digitos [cite: 1061]
    val pin: String // PIN a 6 dígitos [cite: 1062]
)

// Petición para Validación CFDI
data class CfdiRequest(
    val name: String, // nombre [cite: 985]
    val document: String // b64document [cite: 986]
)

// Petición para Guardado de Firma y Biométricos
data class SignatureRequest(
    val grafo: Grafo, [cite: 907]
val grafo2: Grafo? = null, // Opcional, para el asegurado titular [cite: 914, 939]
val name: String, // nombre contratante [cite: 901]
val name2: String? = null, // Opcional [cite: 923]
val solicitud: String, // 12 digitos [cite: 902]
val numtrabajo: String // 10 digitos [cite: 903]
) {
    data class Grafo(
        val time_simple: List<String>?, [cite: 909]
    val clickX_simple: List<Double>?, [cite: 910]
    val clickY_simple: List<Double>?, [cite: 911]
    val pressure: String?, [cite: 912]
    val fileContent: String? // b64 firma png 400x200 px [cite: 898, 913]
    )
}