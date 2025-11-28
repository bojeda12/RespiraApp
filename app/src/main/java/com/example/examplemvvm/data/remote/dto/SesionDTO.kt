package com.example.examplemvvm.data.remote.dto

data class SesionDTO(
    val usuarioId: Int,
    val rutinaId: Int,
    val duracionSegundos: Int,
    val estadoAnimo: Int,
    val horaDelDia: Double,
    val fecha: String
)



