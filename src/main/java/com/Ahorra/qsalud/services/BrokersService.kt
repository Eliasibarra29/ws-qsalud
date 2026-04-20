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

    fun prepararMovimientoCompleto(simplificado: CotizacionSimplificadaRequest, datosCp: CodPosResponse?): MovimientosRequest {
        val hoy = LocalDate.now()
        val fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy")

        if (simplificado.tipoMovimiento == "3") {
            val movimientoEmision = Movimiento(
                tipoMovimiento = "3", noNegocio = "00004", usuario = "AHORRAIO_DEV",
                noCotizacion = simplificado.folioCotizacion, solicitud = simplificado.folioSolicitud,
                subRamo = "37", datosGenerales = null, contratante = null, asegurados = null
            )
            return MovimientosRequest(listOf(MovimientoWrapper(movimientoEmision)))
        }

        val idEstado = datosCp?.data?.estado?.id?.toIntOrNull() ?: 0
        val idMunicipio = datosCp?.data?.alc_mcpio?.id?.toIntOrNull() ?: 0

        val contratante = Contratante(
            tipoPersona = "1", nacionalidadContra = "N", rfcContra = simplificado.rfc,
            nombreContratante = simplificado.nombre, apContratante = simplificado.apellidoPaterno,
            amContratante = simplificado.apellidoMaterno, fechaNacimientoContratante = simplificado.fechaNacimiento,
            generoContratante = simplificado.genero, calleContratante = simplificado.calle,
            noExteriorContra = simplificado.noExterior, cpContratante = simplificado.cp,
            celularContratante = simplificado.celular, emailContratante = simplificado.email,
            ocupacionContratante = simplificado.ocupacion, coloniaContratante = simplificado.idColonia,
            municipioContratante = idMunicipio, estadoContratante = idEstado,
            paisNacimientoContra = 1, entidadFedNacimientoContra = idEstado, tipoDomicilioContra = 2,


            emailFactura = simplificado.emailFactura ?: simplificado.email,
            cpFactura = simplificado.cpFactura ?: simplificado.cp,

            cuestionario492 = if (simplificado.tipoMovimiento == "7" && simplificado.respuestas492 != null) {
                Cuestionario492(preguntas = simplificado.respuestas492.map {
                    Pregunta492(noPreguntaCon = it.id_pregunta, respuestaCon = it.respuesta)
                })
            } else null
        )

        val aseguradosQualitas = simplificado.asegurados.mapIndexed { index, a ->
            val id = String.format("%08d", index + 1)

            val coberturasDinamicas = a.coberturas.map { cob ->
                if (cob.sumaAsegurada <= 0.0) Cobertura("S", cob.noCobertura)
                else Cobertura("S", cob.noCobertura, listOf(ConceptoCB("SUMA ASEGURADA", cob.sumaAsegurada, "MNNA", cob.sumaAsegurada)))
            }

            val beneficiariosQualitas = a.beneficiarios?.mapIndexed { bIndex, b ->
                Beneficiario(
                    noBeneficiario = String.format("%02d", bIndex + 1), nombreBeneficiario = b.nombre,
                    apellidoPatBeneficiario = b.apellidoPaterno, apellidoMatBeneficiario = b.apellidoMaterno,
                    fechaNacimientoBeneficiario = b.fechaNacimiento, parentescoBeneficiario = b.parentesco,
                    generoBeneficiario = b.genero, porcentajeBeneficiario = b.porcentaje
                )
            }

            val cuestionarioMedico = a.respuestasMedicas?.let { respuestas ->
                CuestionarioMedico(resultadoMed = "S", preguntas = respuestas.map { PreguntaMed(it.id_pregunta, it.respuesta) })
            }

            //  CUESTIONARIO 492 DEL ASEGURADO ---
            val cuestionario492Aseg = if (simplificado.tipoMovimiento == "7" && a.respuestas492Aseg != null) {
                Cuestionario492(preguntas = a.respuestas492Aseg.map {
                    Pregunta492(noPreguntaCon = it.id_pregunta, respuestaCon = it.respuesta)
                })
            } else null

            // Consolidamos los cuestionarios
            val cuestionariosObj = if (cuestionarioMedico != null || cuestionario492Aseg != null) {
                CuestionariosAsegurado(cuestionarioMedico, cuestionario492Aseg)
            } else null

            Asegurado(
                certificado = id, tipoAsegurado = if (a.parentesco == 1) "T" else "D",
                parentesco = String.format("%02d", a.parentesco), noInciso = id,
                datosAsegurado = DatosAsegurado(
                    nombreAsegurado = a.nombre, apellidoPatAsegurado = a.apellidoPaterno,
                    apellidoMatAsegurado = a.apellidoMaterno, nacional = "N", genero = a.sexo,
                    fechaNacimiento = a.fecha_nacimiento, rfcAsegurado = a.rfc, email = simplificado.email,
                    celular = simplificado.celular, paisNacimientoAsegurado = 1, ocupacionAsegurado = simplificado.ocupacion
                ),
                coberturas = CoberturasWrapper(coberturasDinamicas),
                beneficiarios = if (beneficiariosQualitas != null) BeneficiariosWrapper(beneficiariosQualitas) else null,
                cuestionarios = cuestionariosObj // <--- Asignamos ambos cuestionarios aquí
            )
        }

        val movimiento = Movimiento(
            tipoMovimiento = simplificado.tipoMovimiento, noNegocio = "00004", usuario = "AHORRAIO_DEV", subRamo = "37",
            noCotizacion = simplificado.folioCotizacion,
            datosGenerales = DatosGenerales(
                fechaInicio = hoy.format(fmt), fechaTermino = hoy.plusYears(1).format(fmt),
                moneda = 0, agente = 340, codigoOficina = 10, codigoProducto = 303700052404L,
                nivelProducto = 2, periodoGracia = 14, autorizacionInfoMedica = "S", documentacionCorreo = "S", cpIva = simplificado.cp,
                // --- NUEVOS CAMPOS ASIGNADOS AQUÍ ---
                formaPago = simplificado.formaPago,
                origenRecursos = simplificado.origenRecursos
            ),
            contratante = contratante,
            asegurados = AseguradosWrapper(aseguradosQualitas)
        )

        return MovimientosRequest(listOf(MovimientoWrapper(movimiento)))
    }

    fun procesarMovimientoDinamico(simplificado: CotizacionSimplificadaRequest, token: String): Mono<String> {
        if (simplificado.tipoMovimiento == "3") {
            val requestCompleto = prepararMovimientoCompleto(simplificado, null)
            return crearMovimiento(requestCompleto, token)
        }

        return catalogosService.getCodPos(simplificado.cp, token)
            .flatMap { datosCp ->
                val requestCompleto = prepararMovimientoCompleto(simplificado, datosCp)
                crearMovimiento(requestCompleto, token)
            }
    }
}