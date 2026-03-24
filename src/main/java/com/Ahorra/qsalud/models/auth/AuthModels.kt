package com.Ahorra.qsalud.models.auth

// --- Modelos para Link de Pago ---
data class LinkPagoAuthRequest(
    val username: String,
    val password: String
)

data class LinkPagoAuthResponse(
    val code: Int?,
    val message: String?,
    val date: String?,
    val token: String?
)

// --- Modelos para SISE (Brokers) ---
data class SiseAuthRequest(
    val user: String,
    val password: String
)

data class SiseAuthResponse(
    val message: String?,
    val usuario: String?,
    val tokens: Tokens?
) {
    data class Tokens(
        val refresh: String?,
        val access: String?
    )
}