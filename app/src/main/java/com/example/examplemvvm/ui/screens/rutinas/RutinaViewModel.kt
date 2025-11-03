package com.example.examplemvvm.ui.screens.rutinas

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.examplemvvm.ui.screens.dashboard.DashboardViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class RutinaViewModel: ViewModel() {

    //definimos las variables para nuestra navegacion
    private val _navigationEvent = MutableSharedFlow<RutinaViewModel.NavigationTarget>()
    val navigationEvent = _navigationEvent.asSharedFlow()

    fun onEvent(event: RutinaEvent){
        when(event){
            is RutinaEvent.btnRespiracionProfundaClicked->{
                viewModelScope.launch {
                    _navigationEvent.emit(RutinaViewModel.NavigationTarget.toRespira)
                }
            }
            is RutinaEvent.btnREspiracionCompletaClicked->{
                viewModelScope.launch {
                    _navigationEvent.emit(RutinaViewModel.NavigationTarget.toRespira)
                }
            }
            is RutinaEvent.btnRespiracionDormirMejorClicked->{
                viewModelScope.launch {
                    _navigationEvent.emit(RutinaViewModel.NavigationTarget.toRespira)
                }
            }
            is RutinaEvent.btnRespiracionAbejaClicked -> {
                viewModelScope.launch {
                    _navigationEvent.emit(RutinaViewModel.NavigationTarget.toRespira)
                }
            }
            is RutinaEvent.btnBackDasboardClicked ->{
                viewModelScope.launch {
                    _navigationEvent.emit(RutinaViewModel.NavigationTarget.toDashboard)
                }
            }
        }

    }
    sealed class NavigationTarget(){
        object toRespira: NavigationTarget()
        object toDashboard: NavigationTarget()
    }
}