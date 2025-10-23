package com.example.examplemvvm.ui.theme.login.ui.screens.login

data class LoginState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String = "",
    val isLoginEnabled: Boolean = false
)