package com.example.examplemvvm.ui.screens.configuracion

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.examplemvvm.data.local.session.SesionManager
import com.example.examplemvvm.notificaciones.NotificacionReceiver
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class ConfiguracionViewModel @Inject constructor(
    private val sesionManager: SesionManager
): ViewModel() {
    //definimos las variables para nuestra navegacion
    private val _navigationEvent = MutableSharedFlow<ConfiguracionViewModel.NavigationTarget>()
    val navigationEvent = _navigationEvent.asSharedFlow()

    fun onEvent(event: ConfiguracionEvent){
        when(event){
            is ConfiguracionEvent.btnBackClicked->{
                viewModelScope.launch {
                    _navigationEvent.emit(ConfiguracionViewModel.NavigationTarget.goToBack)
                }
            }
            is ConfiguracionEvent.btnCerrarSesionClicked -> {
                viewModelScope.launch {
                    _navigationEvent.emit(ConfiguracionViewModel.NavigationTarget.cerrarSesion)
                }
            }
        }

    }
    fun guardarHorarioRespiracion(hora: Int, minuto: Int) {
        val horaTexto = String.format("%02d:%02d", hora, minuto)
        viewModelScope.launch {
            sesionManager.guardarHorarioRespiracion(horaTexto)
        }
    }
    val horarioGuardado: StateFlow<String> = sesionManager.horarioRespiracion.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        "18:00"
    )



    fun programarRecordatorio(context: Context, hora: Int, minuto: Int) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        // ✅ Verificar si el sistema permite alarmas exactas
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (!alarmManager.canScheduleExactAlarms()) {
                Toast.makeText(
                    context,
                    "Tu dispositivo no permite alarmas exactas. Actívalo en configuración.",
                    Toast.LENGTH_LONG
                ).show()
                return
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
        }

        if (calendario.timeInMillis < System.currentTimeMillis()) {
            calendario.add(Calendar.DAY_OF_YEAR, 1)
        }

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            calendario.timeInMillis,
            pendingIntent
        )
    }




    sealed class NavigationTarget(){
        object goToBack: NavigationTarget()
        object cerrarSesion: NavigationTarget()
    }
}