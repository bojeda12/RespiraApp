package com.example.examplemvvm.ui.screens.historial

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.examplemvvm.data.repository.MLRepository
import com.example.examplemvvm.data.remote.response.HorarioItem
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.example.examplemvvm.data.local.session.SesionManager
import com.example.examplemvvm.presentation.sync.MessageSender
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class RecomendacionViewModel @Inject constructor(
    application: Application,
    private val repository: MLRepository,
    private val sessionManager: SesionManager
) : AndroidViewModel(application) {



    // Navegación
    private val _navigationEvent = MutableSharedFlow<NavigationTarget>()
    val navigationEvent = _navigationEvent.asSharedFlow()

    fun onEvent(event: RecomendacionEvent) {
        when (event) {
            is RecomendacionEvent.btnBackClicked -> {
                viewModelScope.launch { _navigationEvent.emit(NavigationTarget.goToBack) }
            }
            is RecomendacionEvent.CargarHorarios -> {
                cargarHorarios()
            }
        }
    }

    sealed class NavigationTarget {
        object goToBack : NavigationTarget()
    }

    // Estado de mi UI
    private val _horarios = MutableStateFlow<List<HorarioItem>>(emptyList())
    val horarios: StateFlow<List<HorarioItem>> = _horarios

    private val _mensaje = MutableStateFlow<UiMessage?>(null)
    val mensaje: StateFlow<UiMessage?> = _mensaje


    // Lógica para consumir la API
    private fun cargarHorarios() {
        viewModelScope.launch {
            val idUsuario = sessionManager.idUsuario.firstOrNull() ?: return@launch
            try {
                val sesiones = repository.obtenerSesionesUsuario(idUsuario)
                val response = repository.enviarSesionesAlBackend(sesiones)

                if (response.isSuccessful) {
                    val body = response.body()
                    val horarios = body?.mejores_horarios ?: emptyList()
                    Log.e("horarios", "mensaje:$horarios")
                    horarios.forEach {
                        Log.d("horarios", "Fracción recibida: ${it.hora}, convertido: ${(it.hora * 24).toInt()}:${(((it.hora * 24) % 1) * 60).toInt()}")
                    }
                    //  Ordenar por estado de ánimo (mayor primero) y luego por hora
                    val mejoresHorarios = horarios
                        .shuffled()   // 👈 mezcla aleatoriamente
                        .take(6)

                    _horarios.value = mejoresHorarios
                    MessageSender.enviarHorarios(getApplication(), mejoresHorarios)

                    val err = body?.error
                    _mensaje.value = if (!err.isNullOrBlank()) {
                        UiMessage(err, UiMessageType.INFO)
                    } else null
                } else {
                    _mensaje.value = UiMessage(
                        "Error HTTP: ${response.code()}",
                        UiMessageType.ERROR
                    )
                }
            } catch (e: Exception) {
                val userMsg = when (e) {
                    is java.net.ConnectException -> "No se pudo conectar con el servidor"
                    is java.net.SocketTimeoutException -> "Error de conexion. Intenta de nuevo más tarde"
                    else -> "Error inesperado: ${e.localizedMessage ?: "sin detalle"}"
                }
                _mensaje.value = UiMessage(userMsg, UiMessageType.ERROR)
                Log.e("API", "Excepción: ${e.message}")
            }
        }
    }
    fun clearMessage() {
        _mensaje.value = null
    }


}