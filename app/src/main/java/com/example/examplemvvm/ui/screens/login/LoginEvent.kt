package com.example.examplemvvm.ui.theme.login.ui.screens.login


sealed class LoginEvent{
    data class EmailChanged(val email:String): LoginEvent()
    data class PasswordChanged(val password: String): LoginEvent()
    object LoginClicked : LoginEvent()/*Se crea un evento unico sin datos asociados
    Es nuestro instancia singleton solo nos idica si algun boton ha sido presionado*/
    object RegistrateClicked : LoginEvent()
}