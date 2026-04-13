package com.Ahorra.qsalud.services

import com.Ahorra.qsalud.models.brokers.*
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Service
class BrokersService(
    private val siseWebClient: WebClient,
    private val catalogosService: CatalogosService
) {

    fun crearMovimiento(request: MovimientosRequest, accessToken: String): Mono<String> {
        return siseWebClient.post()
            .uri("/api/brokers/create")
            .headers { it.setBearerAuth(accessToken) }
            .bodyValue(request)
            .retrieve()
            .bodyToMono(String::class.java)
    }

    fun prepararMovimientoCompleto(simplificado: CotizacionSimplificadaRequest, datosCp: CodPosResponse): MovimientosRequest {
        val hoy = LocalDate.now()
        val fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy")

        val contratante = Contratante(
            tipoPersona = "1", nacionalidadContra = "N", rfcContra = simplificado.rfc,
            nombreContratante = simplificado.nombre, apContratante = simplificado.apellidoPaterno,
            amContratante = simplificado.apellidoMaterno, fechaNacimientoContratante = simplificado.fechaNacimiento,
            generoContratante = simplificado.genero, calleContratante = simplificado.calle,
            noExteriorContra = simplificado.noExterior, cpContratante = simplificado.cp,
            celularContratante = simplificado.celular, emailContratante = simplificado.email,
            ocupacionContratante = simplificado.ocupacion,
            coloniaContratante = simplificado.idColonia,
            municipioContratante = datosCp.municipioId,
            estadoContratante = datosCp.estadoId,
            paisNacimientoContra = 1, entidadFedNacimientoContra = datosCp.estadoId, tipoDomicilioContra = 2,
            cuestionario492 = Cuestionario492(
                preguntas = simplificado.respuestas492.map {
                    Pregunta492(noPreguntaCon = it.id_pregunta, respuestaCon = it.respuesta)
                }
            )
        )

        val aseguradosQualitas = simplificado.asegurados.mapIndexed { index, a ->
            val id = String.format("%08d", index + 1)
            Asegurado(
                certificado = id, tipoAsegurado = if (a.parentesco == 1) "T" else "D",
                parentesco = String.format("%02d", a.parentesco), noInciso = id,
                datosAsegurado = DatosAsegurado(
                    nombreAsegurado = a.nombre, apellidoPatAsegurado = a.apellidoPaterno,
                    apellidoMatAsegurado = a.apellidoMaterno, nacional = "N", genero = a.sexo,
                    fechaNacimiento = a.fecha_nacimiento, rfcAsegurado = a.rfc, email = simplificado.email,
                    celular = simplificado.celular, paisNacimientoAsegurado = 1, ocupacionAsegurado = simplificado.ocupacion
                ),
                coberturas = CoberturasWrapper(listOf(
                    Cobertura("N", "0014"),
                    Cobertura("S", "0019", listOf(ConceptoCB("SUMA ASEGURADA", 80000.0, "MNNA", 80000.0)))
                ))
            )
        }

        val movimiento = Movimiento(
            tipoMovimiento = "2", noNegocio = "00004", usuario = "AHORRAIO_DEV", subRamo = "37",
            datosGenerales = DatosGenerales(
                fechaInicio = hoy.format(fmt),
                fechaTermino = hoy.plusYears(1).format(fmt),
                moneda = 0, agente = 340, formaPago = "A", codigoOficina = 10,
                codigoProducto = 303700052404L, nivelProducto = 2, periodoGracia = 14,
                origenRecursos = 1, autorizacionInfoMedica = "S", documentacionCorreo = "S",
                cpIva = simplificado.cp
            ),
            contratante = contratante,
            asegurados = AseguradosWrapper(aseguradosQualitas)
        )

        return MovimientosRequest(listOf(MovimientoWrapper(movimiento)))
    }

    fun cotizarRapido(simplificado: CotizacionSimplificadaRequest, token: String): Mono<String> {
        return catalogosService.getCodPos(simplificado.cp, token)
            .flatMap { datosCp ->
                val requestCompleto = prepararMovimientoCompleto(simplificado, datosCp)
                crearMovimiento(requestCompleto, token)
            }
    }
}