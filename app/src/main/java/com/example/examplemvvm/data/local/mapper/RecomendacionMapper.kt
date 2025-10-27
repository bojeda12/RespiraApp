package com.example.examplemvvm.data.local.mapper

import com.example.examplemvvm.data.local.entities.RecomendacionEntity
import com.example.examplemvvm.domain.model.Recomendacion

fun RecomendacionEntity.toModel(): Recomendacion =
    Recomendacion(id, tipoRecomendacion)

fun Recomendacion.toEntity(): RecomendacionEntity =
    RecomendacionEntity(id, tipoRecomendacion)