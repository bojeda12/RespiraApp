package com.example.examplemvvm.ui.screens.registro

data class RegistroState(
    val nombreUsuario: String = "",
    val contrasena:String = "",
    val confirmarContrasena: String = "",
    val correo:String = "",
    val estadoAnimo:String = "",
    val hora:Int = 0,
    val minutos: Int = 0,
    val mensaje: String? = null
){
    fun isFormularioValido(): Boolean{
        return nombreUsuario.isNotBlank() &&
                correo.isNotBlank()&&
                contrasena.isNotBlank()&&
                confirmarContrasena.isNotBlank()&&
                contrasena == confirmarContrasena &&
                estadoAnimo.isNotBlank()

    }
}