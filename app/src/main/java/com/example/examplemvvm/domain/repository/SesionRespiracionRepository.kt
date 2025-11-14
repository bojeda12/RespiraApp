package com.example.examplemvvm.domain.repository

import com.example.examplemvvm.data.local.entities.SesionRespiracionEntity

interface SesionRespiracionRepository {
    suspend fun guardarSesion(sesion: SesionRespiracionEntity)
    suspend fun obtenerSesionesUsuario(idUsuario: Int): List<SesionRespiracionEntity>
    suspend fun obtenerUltimaSesion(idUsuario: Int): SesionRespiracionEntity?
}

