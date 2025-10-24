package com.example.examplemvvm.ui.screens.dashboard

import com.example.examplemvvm.ui.screens.registro.RegistroEvent

sealed class DashboardEvent {
    object BtnConfiguracionClicked : DashboardEvent()
    object BntEstadoClicked: DashboardEvent()
    object BtnRespiracionRutinaClicked: DashboardEvent()
    object BtnRutina1Clicked: DashboardEvent()

    object BtnHistorialClicked: DashboardEvent()
}