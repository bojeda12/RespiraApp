package com.example.examplemvvm.data.remote.response

data class HorarioItem(
    val hora: Double,
    val estadoAnimo: Double
)

data class HorarioResponse(
    val mejores_horarios: List<HorarioItem>,
    val error: String? = null
)

