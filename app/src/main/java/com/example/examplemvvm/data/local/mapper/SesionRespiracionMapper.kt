package com.example.examplemvvm.data.local.mapper

import com.example.examplemvvm.data.local.entities.SesionRespiracionEntity
import com.example.examplemvvm.domain.model.SesionRespiracion


fun SesionRespiracionEntity.toModel(): SesionRespiracion =
    SesionRespiracion(id, fecha, duracion, id_usuario, id_tiporespiracion, id_recomendacion)

fun SesionRespiracion.toEntity(): SesionRespiracionEntity =
    SesionRespiracionEntity(id, fecha, duracion, id_usuario, id_tiporespiracion, id_recomendacion)