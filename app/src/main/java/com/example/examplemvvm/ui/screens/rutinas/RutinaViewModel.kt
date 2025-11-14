package com.example.examplemvvm.ui.screens.rutinas

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.examplemvvm.data.local.session.SesionManager
import com.example.examplemvvm.ui.screens.dashboard.DashboardViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RutinaViewModel @Inject constructor(
    private val sessionManager: SesionManager
): ViewModel() {

    //definimos las variables para nuestra navegacion
    private val _navigationEvent = MutableSharedFlow<RutinaViewModel.NavigationTarget>()
    val navigationEvent = _navigationEvent.asSharedFlow()

    fun onEvent(event: RutinaEvent){
        when(event){
            is RutinaEvent.btnRespiracionProfundaClicked->{
                viewModelScope.launch {
                    sessionManager.guardarRutinaId(1)
                    _navigationEvent.emit(RutinaViewModel.NavigationTarget.toRespira(1))
                }
            }
            is RutinaEvent.btnREspiracionCompletaClicked->{
                viewModelScope.launch {
                    sessionManager.guardarRutinaId(2)
                    _navigationEvent.emit(RutinaViewModel.NavigationTarget.toRespira(2))
                }
            }
            is RutinaEvent.btnRespiracionDormirMejorClicked->{
                viewModelScope.launch {
                    sessionManager.guardarRutinaId(3)
                    _navigationEvent.emit(RutinaViewModel.NavigationTarget.toRespira(3))
                }
            }
            is RutinaEvent.btnRespiracionAbejaClicked -> {
                viewModelScope.launch {
                    sessionManager.guardarRutinaId(4)
                    _navigationEvent.emit(RutinaViewModel.NavigationTarget.toRespira(4))
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
        data class toRespira(val rutinaId: Int): NavigationTarget()
        object toDashboard: NavigationTarget()
    }
}