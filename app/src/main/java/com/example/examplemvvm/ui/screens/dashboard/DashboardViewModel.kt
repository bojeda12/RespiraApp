package com.example.examplemvvm.ui.screens.dashboard

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.examplemvvm.data.local.session.SesionManager
import com.example.examplemvvm.domain.repository.EstadoAnimoRepository
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
import com.example.examplemvvm.R
import com.example.examplemvvm.data.local.dao.RegistroEstadoAnimoDao
import com.example.examplemvvm.domain.model.RegistroEstadoAnimo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import java.time.DayOfWeek
import java.time.LocalDate
import javax.inject.Inject
import kotlin.math.roundToInt

//import javax.inject.Inject



@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val sesionManager: SesionManager,
    private val estadoAnimoRepository: EstadoAnimoRepository,
    private val registroEstadoAnimoDao: RegistroEstadoAnimoDao
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
                    //sesionManager.cerrarSesion()
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
    private val ultimoEstadoRegistro = sesionManager.idUsuario
        .filterNotNull()
        .flatMapLatest { id ->
            // flatMapLatest asegura que el flujo de datos no esté anidado.
            estadoAnimoRepository.obtenerUltimoRegistro(id)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

    // 🟢 StateFlow para el Texto del Estado
    val ultimoEstadoTexto: StateFlow<String> = ultimoEstadoRegistro.map { registro ->
        when (registro?.estadoAnimo) {
            "5" -> "Muy bien"
            "4" -> "Bien"
            "3" -> "Neutro"
            "2" -> "Mal"
            "1" -> "Muy Mal"
            else -> "Sin registro"
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = "Cargando..."
    )

    // StateFlow para el Icono del Estado
    val ultimoEstadoIcono: StateFlow<Int> = ultimoEstadoRegistro.map { registro ->
        when (registro?.estadoAnimo) {
            "5" -> R.drawable.happyface
            "4" -> R.drawable.happy
            "3" -> R.drawable.confused
            "2" -> R.drawable.sad
            "1" -> R.drawable.sadface
            else -> R.drawable.estadoa
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = R.drawable.confused
    )

    /*
    val ultimoEstadoTexto = MutableStateFlow("")
    val ultimoEstadoIcono = MutableStateFlow(R.drawable.confused)
    init {
        viewModelScope.launch {
            sesionManager.idUsuario.collect {
                id ->
                if(id!= null){
                    val registro = estadoAnimoRepository.obtenerUltimoRegistro(id)
                    when (registro?.estadoAnimo){
                        "5" ->{
                            ultimoEstadoTexto.value = "Muy bien"
                            ultimoEstadoIcono.value = R.drawable.happyface
                        }
                        "4" ->{
                            ultimoEstadoTexto.value = "Bien"
                            ultimoEstadoIcono.value = R.drawable.happy
                        }
                        "3" ->{
                            ultimoEstadoTexto.value = "Neutro"
                            ultimoEstadoIcono.value = R.drawable.confused
                        }
                        "2" ->{
                            ultimoEstadoTexto.value = "Mal"
                            ultimoEstadoIcono.value = R.drawable.sad
                        }
                        "1" ->{
                            ultimoEstadoTexto.value = "Muy Mal"
                            ultimoEstadoIcono.value = R.drawable.sadface
                        }
                        else -> {
                            ultimoEstadoTexto.value = "Sin registro"
                            ultimoEstadoIcono.value = R.drawable.estadoa
                        }
                    }
                }
            }
        }
    }*/

    //Cargar estados de la grafica
    private val _moodsByDay = MutableStateFlow(List(7) { 0 }) // Lunes a Domingo
    val moodsByDay: StateFlow<List<Int>> = _moodsByDay

    fun cargarEstadoFrecuenteSemanaActual() {
        viewModelScope.launch {
            val idUsuario = sesionManager.idUsuario.first() ?: return@launch

            val hoy = LocalDate.now()
            val inicioSemana = hoy.with(DayOfWeek.MONDAY).toString()
            val finSemana = hoy.with(DayOfWeek.SUNDAY).toString()

            val datos = registroEstadoAnimoDao.obtenerEstadoAnimoMasFrecuentePorDia(idUsuario, inicioSemana, finSemana)
            val mapa = datos.mapNotNull {
                val dia = it.diaSemana.toIntOrNull()
                val emocion = it.estadoAnimo?.toIntOrNull()
                if (dia != null && emocion != null) dia to emocion else null
            }.toMap()


            val orden = listOf(1, 2, 3, 4, 5, 6, 0)
            _moodsByDay.value = orden.map { dia -> mapa[dia] ?: 0 }
        }
    }




    sealed class  NavigationTarget(){
        object Configuracion : NavigationTarget()
        object Estados: NavigationTarget()
        object RespiracionRutina: NavigationTarget()
        object Rutina1: NavigationTarget()
        object Historial: NavigationTarget()
    }
}