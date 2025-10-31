package com.example.examplemvvm.ui.screens.respira


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class RespiraViewModel: ViewModel() {
    //definimos las variables para nuestra navegacion
    private val _navigationEvent = MutableSharedFlow<RespiraViewModel.NavigationTarget>()
    val navigationEvent = _navigationEvent.asSharedFlow()

    fun onEvent(event: RespiraEvent){
        when(event){
            is RespiraEvent.btnDashboardClicked->{
                viewModelScope.launch {
                    _navigationEvent.emit(RespiraViewModel.NavigationTarget.goToDashboard)
                }
            }
        }
    }
    sealed class NavigationTarget(){
        object goToDashboard: NavigationTarget()
    }
}