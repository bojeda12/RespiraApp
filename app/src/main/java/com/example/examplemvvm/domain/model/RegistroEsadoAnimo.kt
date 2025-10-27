package com.example.examplemvvm.domain.model

import java.time.LocalDate

data class RegistroEstadoAnimo(
    val id: Int,
    val fecha: String,
    val estadoAnimo: String,
    val id_usuario: Int
)