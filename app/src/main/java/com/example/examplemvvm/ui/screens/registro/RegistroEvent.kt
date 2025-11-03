package com.example.examplemvvm.ui.screens.registro

sealed class RegistroEvent{
    data class nombreUsuarioChanged(val nombreUsuario: String): RegistroEvent()
    data class contrasenaChanged(val contrasena:String): RegistroEvent()
    data class confirmarContrasenaChanged(val confirmarContrasena:String): RegistroEvent()
    data class correoChanged(val correo: String): RegistroEvent()
    data class estadoAnimoChanged(val estado: String): RegistroEvent()
    data class horaRespiracionChanged(val hora: Int): RegistroEvent()
    data class minutoRespiracionchanged(val minuto: Int): RegistroEvent()
    object BackClicked : RegistroEvent() //Eventos que estan inicializados solo escuchando a que se presionen
    object RegistroClicked: RegistroEvent()
    object ObtenerUsuarios : RegistroEvent()

}