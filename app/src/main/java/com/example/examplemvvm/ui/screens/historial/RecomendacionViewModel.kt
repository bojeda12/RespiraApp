package com.example.examplemvvm.ui.screens.historial

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.examplemvvm.data.repository.MLRepository
import com.example.examplemvvm.data.remote.response.HorarioItem
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import android.util.Log
import com.example.examplemvvm.data.local.session.SesionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RecomendacionViewModel @Inject constructor(
    private val repository: MLRepository,
    private val sessionManager: SesionManager
) : ViewModel() {

    // 👉 Navegación
    private val _navigationEvent = MutableSharedFlow<NavigationTarget>()
    val navigationEvent = _navigationEvent.asSharedFlow()

    fun onEvent(event: RecomendacionEvent) {
        when (event) {
            is RecomendacionEvent.btnBackClicked -> {
                viewModelScope.launch {
                    _navigationEvent.emit(NavigationTarget.goToBack)
                }
            }
            is RecomendacionEvent.CargarHorarios -> {
                cargarHorarios()
            }
        }
    }

    sealed class NavigationTarget {
        object goToBack : NavigationTarget()
    }

    // 👉 Estado de la UI
    private val _horarios = MutableStateFlow<List<HorarioItem>>(emptyList())
    val horarios: StateFlow<List<HorarioItem>> = _horarios

    private val _mensaje = MutableStateFlow<String?>(null)
    val mensaje: StateFlow<String?> = _mensaje

    // 👉 Lógica para consumir la API
    private fun cargarHorarios() {
        viewModelScope.launch {
            val idUsuario = sessionManager.idUsuario.firstOrNull() ?: return@launch

            try {
                val sesiones = repository.obtenerSesionesUsuario(idUsuario)
                val response = repository.enviarSesionesAlBackend(sesiones)
                if (response.isSuccessful) {
                    _horarios.value = response.body()?.mejores_horarios ?: emptyList()
                    Log.d("API", "Respuesta: ${response.body()?.mejores_horarios}")
                } else {
                    _mensaje.value = "Error HTTP: ${response.code()}"
                }
            } catch (e: Exception) {
                _mensaje.value = "Excepción: ${e.message}"
                Log.e("API", "Excepción: ${e.message}")
            }
        }
    }
}