package com.example.examplemvvm.data.repository

import com.example.examplemvvm.data.local.dao.SesionRespiracionDao
import com.example.examplemvvm.data.local.dao.RegistroEstadoAnimoDao
import com.example.examplemvvm.data.remote.api.RetrofitInstance
import com.example.examplemvvm.data.remote.dto.SesionDTO
import com.example.examplemvvm.data.remote.response.HorarioResponse
import retrofit2.Response
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.Duration

class MLRepository(
    private val sesionDao: SesionRespiracionDao,
    private val estadoDao: RegistroEstadoAnimoDao
) {

    // 🔹 Helper para convertir "HH:mm" a fracción del día
    private fun String.toFraccionDelDia(): Double {
        return try {
            val partes = this.split(":")
            val hora = partes[0].toInt()
            val minuto = partes[1].toInt()
            (hora + minuto / 60.0) / 24.0
        } catch (e: Exception) {
            0.0 // fallback si el formato no es válido
        }
    }

    suspend fun obtenerSesionesUsuario(idUsuario: Int): List<SesionDTO> {
        val sesiones = sesionDao.obtenerSesionesUsuario(idUsuario)
        val estados = estadoDao.obtenerEstadosUsuario(idUsuario)

        val formatterEstado = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val formatterSesion = DateTimeFormatter.ofPattern("yyyyMMdd") // ← corregido
        val formatterSalida = DateTimeFormatter.ofPattern("yyyyMMdd")

        return sesiones.map { sesion ->
            // Parsear fecha de sesión (formato yyyyMMdd)
            val fechaSesion = try {
                LocalDate.parse(sesion.fecha, formatterSesion)
            } catch (e: Exception) {
                null
            }

            // Parsear horaInicio como minutos desde medianoche
            val horaSesion = try {
                val partes = sesion.horaInicio.split(":")
                val h = partes[0].toInt()
                val m = partes[1].toInt()
                h * 60 + m
            } catch (e: Exception) {
                0
            }

            // Buscar estado más cercano dentro del mismo día
            val estadoRelacionado = fechaSesion?.let { fs ->
                estados
                    .mapNotNull { estado ->
                        try {
                            val fechaEstado = LocalDateTime.parse(estado.fecha, formatterEstado)
                            if (fechaEstado.toLocalDate() == fs) {
                                val minutosEstado = fechaEstado.hour * 60 + fechaEstado.minute
                                Pair(kotlin.math.abs(horaSesion - minutosEstado), estado.estadoAnimo.toIntOrNull())
                            } else null
                        } catch (e: Exception) {
                            null
                        }
                    }
                    .minByOrNull { it.first }?.second
            } ?: 3

            val fechaFormateada = fechaSesion?.format(formatterSalida) ?: sesion.fecha

            SesionDTO(
                usuarioId = sesion.id_usuario,
                rutinaId = sesion.id_tiporespiracion ?: 0,
                duracionSegundos = sesion.duracion,
                estadoAnimo = estadoRelacionado ?: 3,
                horaDelDia = sesion.horaInicio.toFraccionDelDia(),
                fecha = fechaFormateada
            )
        }
    }

    suspend fun enviarSesionesAlBackend(sesiones: List<SesionDTO>): Response<HorarioResponse> {
        return RetrofitInstance.api.enviarSesiones(sesiones)
    }
}