package com.example.examplemvvm.ui.screens.registro

import androidx.compose.runtime.currentRecomposeScope
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.examplemvvm.ui.theme.login.ui.screens.login.LoginState

class RegistroViewModel: ViewModel() {
    private val _state = MutableLiveData(RegistroState())

    val state: LiveData<RegistroState> = _state

    fun onEvent(event: RegistroEvent){
        val current = _state.value ?: RegistroState()
        when(event){
            is RegistroEvent.NombreUsuarioChanged -> {
                val newNombreUser = event.nombreUsuario
                _state.value = current.copy(nombreUsuario = newNombreUser)
            }

            is RegistroEvent.correoChanged -> {
                val newCorreo = event.correo
                _state.value = current.copy(correo = newCorreo)
            }

            is RegistroEvent.contrasenaChanged->{
                val newContrasena = event.contrasena
                _state.value = current.copy(contrasena = newContrasena)
            }

            is RegistroEvent.confirmarContrasenaChanged -> {
                val newConfirmarContrasena = event.confirmarContrasena
                _state.value = current.copy(confirmarContrasena = newConfirmarContrasena)
            }


        }
    }
}