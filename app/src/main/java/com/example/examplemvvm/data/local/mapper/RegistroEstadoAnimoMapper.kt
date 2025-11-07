package com.example.examplemvvm.data.local.mapper

import com.example.examplemvvm.data.local.entities.RegistroEstadoAnimoEntity
import com.example.examplemvvm.domain.model.RegistroEstadoAnimo


fun RegistroEstadoAnimoEntity.toModel(): RegistroEstadoAnimo =
    RegistroEstadoAnimo(id, fecha, estadoAnimo, id_usuario)

fun RegistroEstadoAnimo.toEntity(idUsuario: Int): RegistroEstadoAnimoEntity =
    RegistroEstadoAnimoEntity(fecha = fecha, estadoAnimo = estadoAnimo,id_usuario = idUsuario)
