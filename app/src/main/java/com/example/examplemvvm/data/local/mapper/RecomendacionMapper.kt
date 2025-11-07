package com.example.examplemvvm.data.local.mapper

import com.example.examplemvvm.data.local.entities.RecomendacionEntity
import com.example.examplemvvm.domain.model.Recomendacion

fun RecomendacionEntity.toModel(): Recomendacion {
    return Recomendacion(id,tipoRecomendacion)
}

fun Recomendacion.toEntity(idUsuario: Int): RecomendacionEntity =
    RecomendacionEntity(id, tipoRecomendacion, id_usuario = idUsuario)