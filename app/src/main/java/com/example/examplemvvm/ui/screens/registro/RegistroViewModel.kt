package com.example.examplemvvm.ui.screens.registro

import androidx.compose.runtime.currentRecomposeScope
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.examplemvvm.ui.theme.login.ui.screens.login.LoginState
import com.example.examplemvvm.ui.theme.login.ui.screens.login.LoginViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class RegistroViewModel: ViewModel() {

    //Aqui decribimos los mutable data de los textfield, basicamente es cuando cambia de estado
    private val _state = MutableLiveData(RegistroState())

    val state: LiveData<RegistroState> = _state

    //Aqui definimos las variables de nuestr navegacion
    private val _navigationEvent = MutableSharedFlow<RegistroViewModel.NavigationTarget>()
    val navigationEvent = _navigationEvent.asSharedFlow()

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
            is RegistroEvent.RegistroClicked -> {
                viewModelScope.launch {
                    _navigationEvent.emit(RegistroViewModel.NavigationTarget.Dashboard)
                }
            }
            is RegistroEvent.BackClicked -> {
                viewModelScope.launch {
                    _navigationEvent.emit(RegistroViewModel.NavigationTarget.Login)
                }
            }


        }
    }
    sealed class NavigationTarget{
        object Login : NavigationTarget()
        object Dashboard : NavigationTarget()
    }
}