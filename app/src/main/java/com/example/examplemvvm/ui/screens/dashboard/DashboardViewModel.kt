package com.example.examplemvvm.ui.screens.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.examplemvvm.data.local.session.SesionManager
import com.example.examplemvvm.ui.screens.registro.RegistroEvent
import com.example.examplemvvm.ui.screens.registro.RegistroViewModel
import com.example.examplemvvm.ui.theme.login.ui.screens.login.LoginViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
//import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

//import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val sesionManager: SesionManager
): ViewModel() {

    //pasamos el nombre de usuario que nos dio el sessionManager
    val nombreUsuario: StateFlow<String> = sesionManager.nombreUsuario
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "")

    // variablepara nuestra grafica
    private val _weekMoods = MutableStateFlow(listOf(1,2,2,3,3,3,5))
    val weekMoods: StateFlow<List<Int>> = _weekMoods

    //Aqui definimos las variables de nuestra navegacion
    private val _navigationEvent = MutableSharedFlow<DashboardViewModel.NavigationTarget>()
    val navigationEvent = _navigationEvent.asSharedFlow()

    fun onEvent(event: DashboardEvent){
        when(event){
            is DashboardEvent.BtnConfiguracionClicked-> {
                viewModelScope.launch {
                    _navigationEvent.emit(DashboardViewModel.NavigationTarget.Configuracion)
                    sesionManager.cerrarSesion()
                }
            }
            is DashboardEvent.BntEstadoClicked -> {
                viewModelScope.launch {
                    _navigationEvent.emit(DashboardViewModel.NavigationTarget.Estados)
                }
            }
            is DashboardEvent.BtnRespiracionRutinaClicked -> {
                viewModelScope.launch {
                    _navigationEvent.emit(DashboardViewModel.NavigationTarget.RespiracionRutina)
                }
            }
            is DashboardEvent.BtnRutina1Clicked -> {
                viewModelScope.launch {
                    _navigationEvent.emit(DashboardViewModel.NavigationTarget.Rutina1)
                }
            }
            is DashboardEvent.BtnHistorialClicked -> {
                viewModelScope.launch {
                    _navigationEvent.emit(DashboardViewModel.NavigationTarget.Historial)
                }
            }
        }
    }
    sealed class  NavigationTarget(){
        object Configuracion : NavigationTarget()
        object Estados: NavigationTarget()
        object RespiracionRutina: NavigationTarget()
        object Rutina1: NavigationTarget()
        object Historial: NavigationTarget()
    }

    // CUANDO CONECTEMOS ROOM (ejemplo):
    // val weekMoods: StateFlow<List<Int>> =
    //     dao.getWeekMoodAverages(System.currentTimeMillis() - 6*24*60*60*1000)
    //        .map { list ->                           // mapear 0..6 → L..D
    //            val byDow = list.associate { it.dow.toInt() to it.avgMood }
    //            val order = listOf(1,2,3,4,5,6,0)    // L..D
    //            order.map { dow -> (byDow[dow] ?: 0f).coerceIn(1f,5f).roundToInt() }
    //        }
    //        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())
}