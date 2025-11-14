package com.example.examplemvvm.ui.screens.estado

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.examplemvvm.domain.model.RegistroEstadoAnimo
import com.example.examplemvvm.domain.repository.EstadoAnimoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class EstadoViewModel @Inject constructor(
    private val estadoAnimoRepository: EstadoAnimoRepository
) : ViewModel() {
    //definimos las variables para nuestra navegacion
    private val _navigationEvent = MutableSharedFlow<EstadoViewModel.NavigationTarget>()
    val navigationEvent = _navigationEvent.asSharedFlow()

    fun onEvent(event: EstadoEvent) {
        when (event) {
            is EstadoEvent.btnBackClicked -> {
                viewModelScope.launch {
                    _navigationEvent.emit(EstadoViewModel.NavigationTarget.ToBack)
                }
            }
            is EstadoEvent.EstadoSeleccionado -> {
                viewModelScope.launch {
                    val registro = RegistroEstadoAnimo(
                        id = 0,
                        fecha = LocalDate.now().toString(),
                        estadoAnimo = event.estado,
                        id_usuario = 0
                    )
                    estadoAnimoRepository.registrarEstado(registro)
                    _navigationEvent.emit(EstadoViewModel.NavigationTarget.ToBack)
                }
            }
        }
    }

    sealed class NavigationTarget() {
        object ToBack : NavigationTarget()
    }
}