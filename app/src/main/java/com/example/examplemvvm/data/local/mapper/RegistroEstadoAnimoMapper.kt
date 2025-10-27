package com.example.examplemvvm.data.local.mapper

import com.example.examplemvvm.data.local.entities.RegistroEstadoAnimoEntity
import com.example.examplemvvm.domain.model.RegistroEstadoAnimo


fun RegistroEstadoAnimoEntity.toModel(): RegistroEstadoAnimo =
    RegistroEstadoAnimo(id, fecha, estadoAnimo, id_usuario)

fun RegistroEstadoAnimo.toEntity(): RegistroEstadoAnimoEntity =
    RegistroEstadoAnimoEntity(id, fecha, estadoAnimo, id_usuario)