package com.example.examplemvvm.domain.model

data class RegistroEstadoAnimo(
    val id: Int,
    val fecha: String,
    val estadoAnimo: String,
    val id_usuario: Int
)