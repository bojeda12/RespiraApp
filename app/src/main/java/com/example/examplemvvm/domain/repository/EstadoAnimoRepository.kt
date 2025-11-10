package com.example.examplemvvm.domain.repository

import com.example.examplemvvm.domain.model.RegistroEstadoAnimo

interface EstadoAnimoRepository {
    suspend fun obtenerUltimoRegistro(idUsuario: Int): RegistroEstadoAnimo?

    suspend fun registrarEstado(registro: RegistroEstadoAnimo)

}