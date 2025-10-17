package com.example.examplemvvm.ui.theme.login.ui.screens.login

import kotlinx.serialization.descriptors.PrimitiveKind

sealed class LoginEvent{
    data class EmailChanged(val email:String): LoginEvent()
    data class PasswordChanged(val password: String): LoginEvent()
    object LoginClicked : LoginEvent()
}