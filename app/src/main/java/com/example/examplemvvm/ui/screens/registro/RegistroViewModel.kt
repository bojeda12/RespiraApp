package com.example.examplemvvm.ui.screens.registro

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.examplemvvm.domain.model.RegistroEstadoAnimo
import com.example.examplemvvm.domain.model.SesionRespiracion
import com.example.examplemvvm.domain.model.Usuario
import com.example.examplemvvm.domain.repository.UsuarioRepository
import com.example.examplemvvm.ui.components.AlertaTipo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class RegistroViewModel @Inject constructor(
    private val usuarioRepository: UsuarioRepository
) : ViewModel() {

    var state = MutableLiveData(RegistroState())
        private set

    private val _navigationEvent = MutableSharedFlow<NavigationTarget>()
    val navigationEvent = _navigationEvent.asSharedFlow()


    //Emitimos el mensaje a nuestra pantalla, desde el modelo
    private val _mensajeUI = MutableSharedFlow<Pair<String, AlertaTipo>>()
    val mensajeUI = _mensajeUI.asSharedFlow()

    //Validaciones Step 1
    suspend fun validarStep1(): Boolean {
        val s = state.value!!

        // Verifica campos vacíos
        if (s.nombreUsuario.isEmpty() || s.correo.isEmpty() || s.contrasena.isEmpty() || s.confirmarContrasena.isEmpty()) {
            emitirMensaje("Por favor completa todos los campos", AlertaTipo.ERROR)
            return false
        }

        //Valida si el correo tiene el formato correcto
        if (!isValidEmail(s.correo)){
            emitirMensaje("Ingresa un correo valido ejemplo: pedro@gmail.com", AlertaTipo.INFO)
            return false
        }

        //Se validan que la contrsena tenga el formato correcto y que coincidan
        if (!validarPassword(s.contrasena , confirmarContrasena = s.confirmarContrasena)) return false

        // Verifica contraseñas coinciden
        if (s.contrasena != s.confirmarContrasena) {
            emitirMensaje("Las contraseñas no coinciden", AlertaTipo.INFO)
            return false
        }

        // Verifica si el correo ya existe en la base de datos
        if (usuarioRepository.existeUsuarioPorCorreo(s.correo)) {
            emitirMensaje("El correo ya está registrado", AlertaTipo.INFO)
            return false
        }
        return true
    }

    fun validarStep2(): Boolean {
        val s = state.value!!
        return if (s.estadoAnimo.isEmpty()) {
            emitirMensaje("Selecciona cómo te sientes hoy", AlertaTipo.INFO)
            false
        } else true
    }

    fun validarStep3(): Boolean {
        val s = state.value!!
        return if (s.hora == 0 || s.minutos == 0) {
            emitirMensaje("Selecciona una hora válida", AlertaTipo.INFO)
            false
        } else true
    }

    fun onEvent(event: RegistroEvent) {
        when (event) {
            is RegistroEvent.nombreUsuarioChanged -> state.value =
                state.value!!.copy(nombreUsuario = event.nombreUsuario)

            is RegistroEvent.contrasenaChanged -> state.value =
                state.value!!.copy(contrasena = event.contrasena)

            is RegistroEvent.confirmarContrasenaChanged -> state.value =
                state.value!!.copy(confirmarContrasena = event.confirmarContrasena)

            is RegistroEvent.correoChanged -> state.value =
                state.value!!.copy(correo = event.correo)

            is RegistroEvent.estadoAnimoChanged -> state.value =
                state.value!!.copy(estadoAnimo = event.estado)

            is RegistroEvent.horaRespiracionChanged -> state.value =
                state.value!!.copy(hora = event.hora)

            is RegistroEvent.minutoRespiracionchanged -> state.value =
                state.value!!.copy(minutos = event.minuto)

            is RegistroEvent.ObtenerUsuarios -> obtenerUsuarios()

            is RegistroEvent.RegistroClicked -> {
                viewModelScope.launch {
                    val s = state.value!!
                    val usuario = Usuario(
                        id = 0,
                        nombre_usuario = s.nombreUsuario,
                        correo = s.correo,
                        contrasena = s.contrasena
                    )
                    val estado = RegistroEstadoAnimo(
                        id = 0,
                        fecha = LocalDate.now().toString(),
                        estadoAnimo = s.estadoAnimo,
                        id_usuario = -1
                    )
                    val sesion = SesionRespiracion(
                        id = 0,
                        fecha = LocalDate.now().toString(),
                        duracion = 0,
                        horaInicio = "%02d:%02d".format(s.hora, s.minutos),
                        id_usuario = -1,
                        id_tiporespiracion = null,
                    )
                    try {
                        usuarioRepository.registrarUsuarioCompleto(usuario, estado, sesion)
                        _navigationEvent.emit(NavigationTarget.Login)
                    } catch (e: Exception) {
                        Log.e("RegistroError", "Error al registrar: ${e.message}")
                    }
                }
            }

            is RegistroEvent.BackClicked -> {
                viewModelScope.launch {
                    _navigationEvent.emit(NavigationTarget.Login)
                }
            }

            else -> {}
        }
    }

    fun emitirMensaje(mensaje: String, tipo: AlertaTipo) {
        viewModelScope.launch {
            _mensajeUI.emit(mensaje to tipo)
        }
    }

    sealed class NavigationTarget {
        object Login : NavigationTarget()
        object Dashboard : NavigationTarget()
    }

    fun obtenerUsuarios() {
        viewModelScope.launch {
            val usuarios = usuarioRepository.getUsuarios()
            Log.d("UsuariosGuardados", usuarios.toString())
        }
    }
    fun validarPassword(contrasena: String, confirmarContrasena: String = ""): Boolean {
        if (contrasena.length < 8) {
            emitirMensaje("La contraseña debe tener al menos 8 caracteres", AlertaTipo.INFO)
            return false
        }
        if (!contrasena.any { it.isUpperCase() }) {
            emitirMensaje("La contraseña debe contener al menos una letra mayúscula", AlertaTipo.INFO)
            return false
        }
        if (!contrasena.any { it.isLowerCase() }) {
            emitirMensaje("La contraseña debe contener al menos una letra minúscula", AlertaTipo.INFO)
            return false
        }
        if (!contrasena.any { it.isDigit() }) {
            emitirMensaje("La contraseña debe contener al menos un número", AlertaTipo.INFO)
            return false
        }
        if (!contrasena.any { "!@#\$%^&*()-_=+{}[]|:;\"'<>,.?/".contains(it) }) {
            emitirMensaje("La contraseña debe contener al menos un carácter especial", AlertaTipo.INFO)
            return false
        }
        if (contrasena != confirmarContrasena) {
            emitirMensaje("Las contraseñas no coinciden", AlertaTipo.INFO)
            return false
        }
        return true
    }

    private fun isValidEmail(email:String): Boolean = Patterns.EMAIL_ADDRESS.matcher(email).matches()
}
