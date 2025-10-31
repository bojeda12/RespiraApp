package com.example.examplemvvm.ui.screens.configuracion

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class ConfiguracionViewModel: ViewModel() {
    //definimos las variables para nuestra navegacion
    private val _navigationEvent = MutableSharedFlow<ConfiguracionViewModel.NavigationTarget>()
    val navigationEvent = _navigationEvent.asSharedFlow()

    fun onEvent(event: ConfiguracionEvent){
        when(event){
            is ConfiguracionEvent.btnBackClicked->{
                viewModelScope.launch {
                    _navigationEvent.emit(ConfiguracionViewModel.NavigationTarget.goToBack)
                }
            }
            is ConfiguracionEvent.btnCerrarSesionClicked -> {
                viewModelScope.launch {
                    _navigationEvent.emit(ConfiguracionViewModel.NavigationTarget.cerrarSesion)
                }
            }
        }

    }

    sealed class NavigationTarget(){
        object goToBack: NavigationTarget()
        object cerrarSesion: NavigationTarget()
    }
}