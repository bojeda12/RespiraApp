package com.example.examplemvvm.ui.screens.componentes

import android.util.Patterns

object Validadores {

    fun esCorreoValido(correo: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(correo).matches()
    }

    fun validarPassword(
        contrasena: String,
        confirmarContrasena: String,
        onError: (String) -> Unit
    ): Boolean {
        if (contrasena.length < 8) {
            onError("La contraseña debe tener al menos 8 caracteres")
            return false
        }
        if (!contrasena.any { it.isUpperCase() }) {
            onError("La contraseña debe contener al menos una letra mayúscula")
            return false
        }
        if (!contrasena.any { it.isLowerCase() }) {
            onError("La contraseña debe contener al menos una letra minúscula")
            return false
        }
        if (!contrasena.any { it.isDigit() }) {
            onError("La contraseña debe contener al menos un número")
            return false
        }
        if (!contrasena.any { "!@#\$%^&*()-_=+{}[]|:;\"'<>,.?/".contains(it) }) {
            onError("La contraseña debe contener al menos un carácter especial")
            return false
        }
        if (contrasena != confirmarContrasena) {
            onError("Las contraseñas no coinciden")
            return false
        }
        return true
    }
}