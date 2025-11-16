package com.example.examplemvvm.ui.screens.configuracion

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.usage.ConfigurationStats
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.examplemvvm.data.local.session.SesionManager
import com.example.examplemvvm.domain.repository.UsuarioRepository
import com.example.examplemvvm.notificaciones.NotificacionReceiver
import com.example.examplemvvm.ui.components.AlertaTipo
import com.example.examplemvvm.ui.screens.componentes.Validadores
import com.example.examplemvvm.ui.screens.registro.RegistroState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class ConfiguracionViewModel @Inject constructor(
    private val sesionManager: SesionManager,
    private val usuarioRepository: UsuarioRepository
) : ViewModel() {

    // Navegación
    private val _navigationEvent = MutableSharedFlow<NavigationTarget>()
    val navigationEvent = _navigationEvent.asSharedFlow()

    var state = MutableLiveData(ConfiguracionState())
        private set

    fun onEvent(event: ConfiguracionEvent) {
        val s = state.value!!
        when (event) {
            is ConfiguracionEvent.btnBackClicked -> {
                viewModelScope.launch {
                    _navigationEvent.emit(NavigationTarget.goToBack)
                }
            }
            is ConfiguracionEvent.btnCerrarSesionClicked -> {
                viewModelScope.launch {
                    sesionManager.cerrarSesion()
                    sesionManager.idUsuario.first {it == null}
                    _navigationEvent.emit(NavigationTarget.cerrarSesion)
                }
            }
            is ConfiguracionEvent.btnActualizarClicked -> {
                cambiarContrasena(s.correo,s.contrasena,s.confirmarContrasena)
            }
            is ConfiguracionEvent.correoChanged ->{
                state.value =  state.value!!.copy(correo = event.correo)
            }
            is ConfiguracionEvent.contrasenaChanged ->{
                state.value =  state.value!!.copy(contrasena = event.contrasena)
            }
            is ConfiguracionEvent.confirmarContrasenaChanged ->{
                state.value =  state.value!!.copy(confirmarContrasena = event.confirmarContrasena)
            }
        }
    }

    // Guardar horario seleccionado
    fun guardarHorarioRespiracion(hora: Int, minuto: Int) {
        val horaTexto = String.format("%02d:%02d", hora, minuto)
        viewModelScope.launch {
            sesionManager.guardarHorarioRespiracion(horaTexto)
        }
    }

    // Leer horario guardado
    val horarioGuardado: StateFlow<String> = sesionManager.horarioRespiracion.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        "18:00"
    )

    // Programar notificación si está activada
    fun programarRecordatorio(context: Context, hora: Int, minuto: Int) {
        viewModelScope.launch {
            val notificacionesActivas = sesionManager.notificacionesActivas.first()
            if (!notificacionesActivas) return@launch

            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                if (!alarmManager.canScheduleExactAlarms()) {
                    Toast.makeText(
                        context,
                        "Tu dispositivo no permite alarmas exactas. Actívalo en configuración.",
                        Toast.LENGTH_LONG
                    ).show()
                    return@launch
                }
            }

            val intent = Intent(context, NotificacionReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(
                context,
                1001,
                intent,
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            )

            val calendario = Calendar.getInstance().apply {
                timeInMillis = System.currentTimeMillis()
                set(Calendar.HOUR_OF_DAY, hora)
                set(Calendar.MINUTE, minuto)
                set(Calendar.SECOND, 0)
                if (timeInMillis < System.currentTimeMillis()) {
                    add(Calendar.DAY_OF_YEAR, 1)
                }
            }

            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                calendario.timeInMillis,
                pendingIntent
            )
        }
    }

    // Estado del switch de notificaciones
    val notificacionesActivas: StateFlow<Boolean> = sesionManager.notificacionesActivas.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        true
    )

    fun cambiarEstadoNotificaciones(activo: Boolean) {
        viewModelScope.launch {
            sesionManager.guardarNotificacionesActivas(activo)
        }
    }
    //Validar los campos del usuario para actualizar la contrasena y coreo


    //Emitimos el mensaje a nuestra pantalla, desde el modelo
    private val _mensajeUI = MutableSharedFlow<Pair<String, AlertaTipo>>()
    val mensajeUI = _mensajeUI.asSharedFlow()
    fun emitirMensaje(mensaje: String, tipo: AlertaTipo) {
        viewModelScope.launch {
            _mensajeUI.emit(mensaje to tipo)
        }
    }
    fun cambiarContrasena(correo: String, nueva: String, confirmar: String) {
        viewModelScope.launch {
            if (correo.isBlank() || nueva.isBlank() || confirmar.isBlank()) {
                emitirMensaje("Completa todos los campos", AlertaTipo.ERROR)
                return@launch
            }

            if (!Validadores.esCorreoValido(correo)) {
                emitirMensaje("Correo inválido", AlertaTipo.INFO)
                return@launch
            }

            val esValida = Validadores.validarPassword(nueva, confirmar) {
                emitirMensaje(it, AlertaTipo.INFO)
            }
            if (!esValida) return@launch

            val usuario = usuarioRepository.existeUsuarioPorCorreo(correo)
            if (usuario == null) {
                emitirMensaje("Correo no registrado", AlertaTipo.ERROR)
                return@launch
            }

            val actualizado = usuarioRepository.actualizarContrasena(correo, nueva)
            if (actualizado) {
                emitirMensaje("Contraseña actualizada correctamente", AlertaTipo.EXITO)
                limpiarFormulario()
            } else {
                emitirMensaje("Error al actualizar la contraseña", AlertaTipo.ERROR)
            }
        }
    }


    //Limpiar campos
    fun limpiarFormulario() {
        state.value = ConfiguracionState()
    }


    // Destinos de navegación
    sealed class NavigationTarget {
        object goToBack : NavigationTarget()
        object cerrarSesion : NavigationTarget()
    }
}