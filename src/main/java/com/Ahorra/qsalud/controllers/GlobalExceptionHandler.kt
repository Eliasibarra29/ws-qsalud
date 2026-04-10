package com.Ahorra.qsalud.controllers

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.reactive.function.client.WebClientResponseException
import reactor.core.publisher.Mono

@RestControllerAdvice
class GlobalExceptionHandler {

    /**
     * Intercepta todas las excepciones HTTP generadas por WebClient (Errores 400, 401, 404, 500 de Quálitas).
     * Extrae el body de error original y lo reenvía al frontend con el mismo HTTP Status.
     */
    @ExceptionHandler(WebClientResponseException::class)
    fun handleWebClientResponseException(ex: WebClientResponseException): Mono<ResponseEntity<String>> {

        return Mono.just(
            ResponseEntity
                .status(ex.statusCode)
                .body(ex.responseBodyAsString)
        )
    }


    @ExceptionHandler(Exception::class)
    fun handleGeneralException(ex: Exception): Mono<ResponseEntity<String>> {
        val errorJson = """
            {
                "codigoError": "INTERNAL_ERROR",
                "mensajeError": "Error interno del servidor API Ahorra",
                "detalle": "${ex.message?.replace("\"", "\\\"")}"
            }
        """.trimIndent()

        return Mono.just(
            ResponseEntity
                .internalServerError()
                .body(errorJson)
        )
    }
}