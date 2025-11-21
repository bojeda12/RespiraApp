package com.example.examplemvvm.domain.repository

import com.example.examplemvvm.domain.model.RegistroEstadoAnimo
import kotlinx.coroutines.flow.Flow

interface EstadoAnimoRepository {
    fun obtenerUltimoRegistro(idUsuario: Int): Flow<RegistroEstadoAnimo?>

    suspend fun registrarEstado(registro: RegistroEstadoAnimo)

}