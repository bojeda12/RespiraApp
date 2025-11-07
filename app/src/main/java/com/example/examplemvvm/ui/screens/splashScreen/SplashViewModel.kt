package com.example.examplemvvm.ui.screens.splashScreen


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.examplemvvm.data.local.session.SesionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    sesionManager: SesionManager
) : ViewModel() {

    val haySesion = sesionManager.idUsuario
        .map { it != null }
        .stateIn(viewModelScope, SharingStarted.Eagerly, false)
}