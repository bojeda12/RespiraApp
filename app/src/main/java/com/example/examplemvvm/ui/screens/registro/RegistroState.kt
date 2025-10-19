package com.example.examplemvvm.ui.screens.registro

data class RegistroState(
    val nombreUsuario: String = "",
    val contrasena:String = "",
    val confirmarContrasena: String = "",
    val correo:String = "",
    val hora:String = "",
    val minutos:String = ""
)