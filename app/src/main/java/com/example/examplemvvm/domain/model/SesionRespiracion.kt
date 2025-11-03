package com.example.examplemvvm.domain.model

import java.time.LocalDate

data class SesionRespiracion(
    val id: Int,
    val fecha: String,
    val duracion: Int,
    val horaInicio: String,
    val id_usuario: Int,
    val id_tiporespiracion: Int? = null,
)