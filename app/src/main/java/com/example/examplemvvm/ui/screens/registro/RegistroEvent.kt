package com.example.examplemvvm.ui.screens.registro

sealed class RegistroEvent{
    data class NombreUsuarioChanged(val nombreUsuario: String): RegistroEvent()
    data class contrasenaChanged(val contrasena:String): RegistroEvent()
    data class confirmarContrasenaChanged(val confirmarContrasena:String): RegistroEvent()
    data class correoChanged(val correo: String): RegistroEvent()
    object BackClicked : RegistroEvent() //Eventos que estan inicializados solo escuchando a que se presionen
    object RegistroClicked: RegistroEvent()
}