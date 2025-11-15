package com.example.examplemvvm.ui.screens.splashScreen


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.examplemvvm.data.local.session.SesionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val sesionManager: SesionManager
) : ViewModel() {

    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    private val _haySesion = MutableStateFlow<Boolean?>(null)
    val haySesion = _haySesion.asStateFlow()

    init {
        viewModelScope.launch {
            delay(1000) // si quieres simular carga
            val idUsuario = sesionManager.idUsuario.firstOrNull()
            _haySesion.value = idUsuario != null && idUsuario != 0
            _isLoading.value = false // ✅ Ya terminó de cargar
        }
    }
}



