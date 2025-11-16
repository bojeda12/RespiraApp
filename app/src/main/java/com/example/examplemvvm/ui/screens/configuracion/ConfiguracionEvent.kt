package com.example.examplemvvm.ui.screens.configuracion

import com.example.examplemvvm.ui.screens.registro.RegistroEvent

sealed class ConfiguracionEvent {
    object btnBackClicked: ConfiguracionEvent()
    object btnCerrarSesionClicked: ConfiguracionEvent()
    object btnActualizarClicked: ConfiguracionEvent()
    data class contrasenaChanged(val contrasena:String): ConfiguracionEvent()
    data class confirmarContrasenaChanged(val confirmarContrasena:String): ConfiguracionEvent()
    data class correoChanged(val correo: String): ConfiguracionEvent()
}