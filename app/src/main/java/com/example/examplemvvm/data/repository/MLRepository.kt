package com.example.examplemvvm.data.repository

import com.example.examplemvvm.data.local.dao.SesionRespiracionDao
import com.example.examplemvvm.data.local.dao.RegistroEstadoAnimoDao
import com.example.examplemvvm.data.remote.api.RetrofitInstance
import com.example.examplemvvm.data.remote.dto.SesionDTO
import com.example.examplemvvm.data.remote.response.HorarioResponse
import kotlinx.coroutines.flow.lastOrNull
import retrofit2.Response

class MLRepository(
    private val sesionDao: SesionRespiracionDao,
    private val estadoDao: RegistroEstadoAnimoDao
) {
    suspend fun obtenerSesionesUsuario(idUsuario: Int): List<SesionDTO> {
        val sesiones = sesionDao.obtenerSesionesUsuario(idUsuario)
        val estados = estadoDao.obtenerEstadosUsuario(idUsuario)

        // Mapear sesiones + último estado de ánimo
        return sesiones.map { sesion ->
            val estado = estados.lastOrNull()?.estadoAnimo?.toIntOrNull() ?: 3
            SesionDTO(
                usuarioId = sesion.id_usuario,
                rutinaId = sesion.id_tiporespiracion ?: 0,
                duracionSegundos = sesion.duracion,
                estadoAnimo = estado,
                horaDelDia = sesion.horaInicio,
                fecha = sesion.fecha
            )
        }
    }

    suspend fun enviarSesionesAlBackend(sesiones: List<SesionDTO>): Response<HorarioResponse> {
        return RetrofitInstance.api.enviarSesiones(sesiones)
    }
}
