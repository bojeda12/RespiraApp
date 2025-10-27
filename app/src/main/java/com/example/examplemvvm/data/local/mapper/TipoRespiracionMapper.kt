package com.example.examplemvvm.data.local.mapper

import com.example.examplemvvm.data.local.entities.TipoRespiracionEntity
import com.example.examplemvvm.domain.model.TipoRespiracion

fun TipoRespiracionEntity.toModel(): TipoRespiracion =
    TipoRespiracion(id, Nom_Respiracion)

fun TipoRespiracion.toEntity(): TipoRespiracionEntity =
    TipoRespiracionEntity(id, Nom_Respiracion)