package com.example.examplemvvm.data.repository

import com.example.examplemvvm.data.local.dao.RegistroEstadoAnimoDao
import com.example.examplemvvm.data.local.mapper.toEntity
import com.example.examplemvvm.data.local.mapper.toModel
import com.example.examplemvvm.data.local.session.SesionManager
import com.example.examplemvvm.domain.model.RegistroEstadoAnimo
import com.example.examplemvvm.domain.repository.EstadoAnimoRepository
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class EstadoAnimoRepositoryImpl @Inject constructor(
    private val estadoAnimodao: RegistroEstadoAnimoDao,
    private val sesionManager: SesionManager
) : EstadoAnimoRepository {
    override suspend fun obtenerUltimoRegistro(idUsuario: Int): RegistroEstadoAnimo? {
        return estadoAnimodao.obtenerUltimoEstado(idUsuario)?.toModel()
    }
    override suspend fun registrarEstado(registro: RegistroEstadoAnimo) {
        val idUsuario = sesionManager.idUsuario.firstOrNull()?: throw IllegalStateException("No hay usuario logueado")
        try{
            estadoAnimodao.insertarEstado(registro.toEntity(idUsuario))
        }catch (e: Exception){
            throw e
        }
    }
}

