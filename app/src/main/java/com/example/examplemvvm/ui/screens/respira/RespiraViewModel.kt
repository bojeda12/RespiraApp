package com.example.examplemvvm.ui.screens.respira


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.examplemvvm.data.local.dao.TipoRespiracionDao
import com.example.examplemvvm.data.local.entities.SesionRespiracionEntity
import com.example.examplemvvm.data.local.entities.TipoRespiracionEntity
import com.example.examplemvvm.data.local.session.SesionManager
import com.example.examplemvvm.domain.repository.SesionRespiracionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject
@HiltViewModel
class RespiraViewModel @Inject constructor(
    private val sesionManager: SesionManager,
    private val sesionRepository: SesionRespiracionRepository
): ViewModel() {

    //definimos las variables para nuestra navegacion
    private val _navigationEvent = MutableSharedFlow<RespiraViewModel.NavigationTarget>()
    val navigationEvent = _navigationEvent.asSharedFlow()

    //definimos las variables y los eventos observables del cronometro
    private val _cronometroActivo = MutableStateFlow(false)
    val cronometroActivo = _cronometroActivo.asStateFlow()

    private val _tiempoTranscurrido = MutableStateFlow(0L) // millis
    val tiempoTranscurrido = _tiempoTranscurrido.asStateFlow()

     private var rutinaIdLocal: Int = 0
     private var idUser:Int =0
    private var horaInicioMillisLocal : Long = 0L

    fun iniciarRutina() {
        viewModelScope.launch {
           val rutinaId = sesionManager.rutinaId.firstOrNull()
            val idUsuario = sesionManager.idUsuario.firstOrNull() ?: 0
            if (rutinaId != null) {
                rutinaIdLocal = rutinaId
                idUser = idUsuario
                horaInicioMillisLocal = System.currentTimeMillis()
                _cronometroActivo.value = true

                val inicio = horaInicioMillisLocal
                while (_cronometroActivo.value) {
                    _tiempoTranscurrido.value = System.currentTimeMillis() - inicio
                    delay(500L)
                }
            } else {
                // Si no hay rutinaId en sesión, puedes navegar al dashboard o mostrar error
                _navigationEvent.emit(NavigationTarget.GoToDashboard)
            }
        }
    }

    fun detenerYGuardar() {
        _cronometroActivo.value = false
        viewModelScope.launch {
            val duracionSegundos = (_tiempoTranscurrido.value / 1000L).toInt()
            val fecha = LocalDate.now().toString() // yyyy-MM-dd
            val horaInicioFormateada = formatMillisToHourMinute(horaInicioMillisLocal)
            val idUsuario = sesionManager.idUsuario.firstOrNull() ?: 0
            if (idUsuario == null) {
                return@launch
            }
            val entidad = SesionRespiracionEntity(
                fecha = fecha,
                duracion = duracionSegundos,
                horaInicio = horaInicioFormateada,
                id_usuario = idUsuario,
                id_tiporespiracion = rutinaIdLocal
            )

            sesionRepository.guardarSesion(entidad)

            // guardar solo id de la rutina en DataStore/session
            //sesionManager.guardarRutinaId(rutinaIdLocal)

            // limpiar estado
            _tiempoTranscurrido.value = 0L
            horaInicioMillisLocal = 0L
            rutinaIdLocal = 0

            _navigationEvent.emit(NavigationTarget.GoToDashboard)
        }
    }

    fun onEvent(event: RespiraEvent){
        when(event){
            is RespiraEvent.btnDashboardClicked->{
                viewModelScope.launch {
                    _navigationEvent.emit(RespiraViewModel.NavigationTarget.GoToDashboard)
                }
            }
            is RespiraEvent.BtnStartClicked -> iniciarRutina()
            is RespiraEvent.BtnStopClicked -> detenerYGuardar()
        }
    }
    private fun formatMillisToHourMinute(millis: Long): String {
        val zdt = java.time.Instant.ofEpochMilli(millis).atZone(java.time.ZoneId.systemDefault())
        val hh = zdt.hour.toString().padStart(2, '0')
        val mm = zdt.minute.toString().padStart(2, '0')
        return "%02d:%02d".format(hh.toInt(), mm.toInt())
    }


    sealed class NavigationTarget(){
        object GoToDashboard: NavigationTarget()
    }

}