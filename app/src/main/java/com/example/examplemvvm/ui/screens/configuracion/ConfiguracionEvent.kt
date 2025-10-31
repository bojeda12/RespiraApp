package com.example.examplemvvm.ui.screens.configuracion

sealed class ConfiguracionEvent {
    object btnBackClicked: ConfiguracionEvent()
    object btnCerrarSesionClicked: ConfiguracionEvent()
}