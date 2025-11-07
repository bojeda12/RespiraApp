package com.example.examplemvvm.data.local.mapper

import com.example.examplemvvm.data.local.entities.SesionRespiracionEntity
import com.example.examplemvvm.domain.model.SesionRespiracion


fun SesionRespiracionEntity.toModel(): SesionRespiracion =
    SesionRespiracion(id, fecha, duracion,horaInicio, id_usuario, id_tiporespiracion)

fun SesionRespiracion.toEntity(idUsuario: Int): SesionRespiracionEntity =
    SesionRespiracionEntity(
        fecha = fecha,
        duracion = duracion,
        horaInicio = horaInicio,
        id_usuario = idUsuario,
        id_tiporespiracion = id_tiporespiracion,
    )
