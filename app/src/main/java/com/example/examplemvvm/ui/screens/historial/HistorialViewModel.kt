package com.example.examplemvvm.ui.screens.historial

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.examplemvvm.ui.screens.registro.RegistroEvent
import com.example.examplemvvm.ui.screens.registro.RegistroViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class HistorialViewModel: ViewModel() {

    //Aqui definimos las variables de nuestro navegacion
    private val _navigationEvent = MutableSharedFlow<HistorialViewModel.NavigationTarget>()
    val navigationEvent = _navigationEvent.asSharedFlow()
    fun onEvent(event: HistorialEvent){
        when(event){
            is HistorialEvent.btnBackClicked -> {
                viewModelScope.launch {
                    _navigationEvent.emit(HistorialViewModel.NavigationTarget.goToBack)
                }
            }
        }
    }


    sealed class NavigationTarget(){
        object goToBack: NavigationTarget()
    }
}