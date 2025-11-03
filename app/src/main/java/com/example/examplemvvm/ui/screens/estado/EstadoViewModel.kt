package com.example.examplemvvm.ui.screens.estado

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.examplemvvm.ui.screens.rutinas.RutinaViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class EstadoViewModel : ViewModel() {
    //definimos las variables para nuestra navegacion
    private val _navigationEvent = MutableSharedFlow<EstadoViewModel.NavigationTarget>()
    val navigationEvent = _navigationEvent.asSharedFlow()

    fun onEvent(event: EstadoEvent) {
        when (event) {
            is EstadoEvent.btnBackClicked -> {
                viewModelScope.launch {
                    _navigationEvent.emit(EstadoViewModel.NavigationTarget.toBack)
                }
            }
        }
    }

    sealed class NavigationTarget() {
        object toBack : NavigationTarget()
    }
}