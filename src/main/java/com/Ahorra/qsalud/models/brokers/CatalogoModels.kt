package com.Ahorra.qsalud.models.brokers


data class CatalogoResponse(
    val data: Any?
)

// Para el cuestionario médico
data class PreguntaCatalogo(
    val id: Int?,
    val codigo_pregunta: String?,
    val pregunta: String?,
    val respuesta: List<Any>?,
    val opciones: Any?
)