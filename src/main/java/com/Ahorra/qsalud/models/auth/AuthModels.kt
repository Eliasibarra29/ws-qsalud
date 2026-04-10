package com.Ahorra.qsalud.models.auth

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

// --- Modelos para Link de Pago ---
@JsonInclude(JsonInclude.Include.NON_NULL)
data class LinkPagoAuthRequest(
    @JsonProperty("username") val username: String,
    @JsonProperty("password") val password: String
)

@JsonInclude(JsonInclude.Include.NON_NULL)
data class LinkPagoAuthResponse(
    val code: Int?,
    val message: String?,
    val date: String?,
    val token: String?
)

// --- Modelos para SISE (Brokers) ---
@JsonInclude(JsonInclude.Include.NON_NULL)
data class SiseAuthRequest(
    @JsonProperty("user") val user: String,
    @JsonProperty("password") val password: String
)

@JsonInclude(JsonInclude.Include.NON_NULL)
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