package com.example.examplemvvm.data.repository

import com.example.examplemvvm.data.local.dao.SesionRespiracionDao
import com.example.examplemvvm.data.local.entities.SesionRespiracionEntity
import com.example.examplemvvm.domain.repository.SesionRespiracionRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SesionRespiracionRepositoryImpl @Inject constructor(
    private val sesionRespiracionDao: SesionRespiracionDao
) : SesionRespiracionRepository {
    override suspend fun guardarSesion(sesion: SesionRespiracionEntity) {
        sesionRespiracionDao.guardarSesion(sesion)
    }

    override suspend fun obtenerSesionesUsuario(idUsuario: Int): List<SesionRespiracionEntity> =
        sesionRespiracionDao.obtenerSesionesUsuario(idUsuario)

    override suspend fun obtenerUltimaSesion(idUsuario: Int): SesionRespiracionEntity? =
        sesionRespiracionDao.obtenerUltimaSesion(idUsuario)
}
