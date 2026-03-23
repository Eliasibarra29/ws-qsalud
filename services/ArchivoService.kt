package com.Ahorra.qsalud.services

import org.springframework.core.io.Resource
import org.springframework.http.MediaType
import org.springframework.http.client.MultipartBodyBuilder
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Service
class ArchivosService(
    private val siseWebClient: WebClient
) {
    // Validación de INE (Frente y Reverso)
    fun validarIdentificacion(fileFront: Resource, fileBack: Resource, token: String): Mono<String> {
        val builder = MultipartBodyBuilder()
        builder.part("file_front", fileFront) [cite: 1153]
        builder.part("file_back", fileBack) [cite: 1153]

        return siseWebClient.post()
            .uri("/api/brokers/upload-identification") [cite: 1149-1150]
        .headers { it.setBearerAuth(token) }
            .contentType(MediaType.MULTIPART_FORM_DATA)
            .body(BodyInserters.fromMultipartData(builder.build()))
            .retrieve()
            .bodyToMono(String::class.java)
    }

    // Validación de Identidad con Video
    fun validarVideo(imageFront: Resource, imageBack: Resource, video: Resource, token: String): Mono<String> {
        val builder = MultipartBodyBuilder()
        builder.part("image_front", imageFront)
        builder.part("image_back", imageBack)
        builder.part("video", video)

        return siseWebClient.post()
            .uri("/api/brokers/upload-video")
            .headers { it.setBearerAuth(token) }
            .contentType(MediaType.MULTIPART_FORM_DATA)
            .body(BodyInserters.fromMultipartData(builder.build()))
            .retrieve()
            .bodyToMono(String::class.java)
    }

    // Validación de Comprobante de Domicilio
    fun validarComprobanteDomicilio(file: Resource, token: String): Mono<String> {
        val builder = MultipartBodyBuilder()
        builder.part("file", file) [cite: 1194]

        return siseWebClient.post()
            .uri("/api/brokers/upload-proof_of-address") [cite: 1190-1191]
        .headers { it.setBearerAuth(token) }
            .contentType(MediaType.MULTIPART_FORM_DATA)
            .body(BodyInserters.fromMultipartData(builder.build()))
            .retrieve()
            .bodyToMono(String::class.java)
    }
}