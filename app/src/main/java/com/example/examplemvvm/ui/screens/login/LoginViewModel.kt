package com.example.examplemvvm.ui.theme.login.ui.screens.login

import android.util.Log
import android.util.Patterns
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.examplemvvm.data.local.session.SesionManager
import com.example.examplemvvm.domain.repository.UsuarioRepository
import com.example.examplemvvm.ui.components.AlertaTipo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/*
    Debemos de importar las librerias necesarias para poder trabajar con la arquitecruta MVVM
    //view model
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.9.4")
    //livedata
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.9.4")
    implementation("androidx.compose.runtime:runtime-livedata:1.9.3")``
    //fragment
    implementation("androidx.fragment:fragment-ktx:1.8.9")
    //Activity
    implementation("androidx.activity:activity-ktx:1.11.0")

    */
//Creamos los estados con liveData aqui dentro
@HiltViewModel
class LoginViewModel @Inject constructor(
    private val usuarioRepository: UsuarioRepository,
    private val sesionManager: SesionManager
) : ViewModel() {
    private val _state = MutableLiveData(LoginState())
    val state: LiveData<LoginState> = _state
    private val _navigationEvent = MutableSharedFlow<NavigationTarget>()
    val navigationEvent = _navigationEvent.asSharedFlow()

    //Creamos las variables para poder enviar el mensaje al UI de error
    private val _emailError = mutableStateOf("")
    val emailError: State<String> = _emailError

    private val _passwordError = mutableStateOf("")
    val passwordError: State<String> = _passwordError
    private val _alertaTipo = mutableStateOf(AlertaTipo.INFO)
    val alertaTipo: State<AlertaTipo> = _alertaTipo

    fun onEvent(event: LoginEvent){
        val current = _state.value ?: LoginState()
        when(event){
            is LoginEvent.EmailChanged -> {
                val newEmail = event.email
                //val isValid = isValidEmail(newEmail) && isValidPassword(current.password)
                _state.value = current.copy(email=newEmail)
            }

            is LoginEvent.PasswordChanged -> {
                val newPassword = event.password
               // val isValid = isValidEmail(current.email) && isValidPassword(newPassword)
                _state.value = current.copy(password = newPassword)
            }
            //Aqui manejamos la navegacion
            is LoginEvent.LoginClicked -> {
                iniciarSesion(current.email,current.password)
            }
            is LoginEvent.RegistrateClicked->{
                viewModelScope.launch {
                    _navigationEvent.emit(NavigationTarget.Registro)
                }
            }
        }

    }
    fun iniciarSesion(correo: String, contrasena: String) {
        viewModelScope.launch {
            // 1️⃣ Limpiar errores previos
            _emailError.value = ""
            _passwordError.value = ""

            var valido = true

            // 2️⃣ Validar campos vacíos
            if (correo.isEmpty()) {
                _emailError.value = "El correo es obligatorio"
                valido = false
            }
            if (contrasena.isEmpty()) {
                _passwordError.value = "La contraseña es obligatoria"
                valido = false
            }

            // Si hay errores, no continuar
            if (!valido) return@launch

            // 3️⃣ Buscar usuario
            val usuario = usuarioRepository.getUsuarios()
                .find { it.correo == correo && it.contrasena == contrasena }

            // 4️⃣ Manejar resultado
            if (usuario != null) {
                sesionManager.guardarSesion(usuario.id, usuario.nombre_usuario)
                _navigationEvent.emit(NavigationTarget.Dashboard)
            } else {
                // Usuario no encontrado → mostrar error en ambos campos o solo uno
                _emailError.value = "Correo o contraseña incorrectos"
                _passwordError.value = "Correo o contraseña incorrectos"
            }
        }
    }
    fun validarCampos(correo: String, contrasena: String): Boolean {
        var valido = true

        if (correo.isEmpty()) {
            _emailError.value = "El correo es obligatorio"
            valido = false
        } else {
            _emailError.value = ""
        }

        if (contrasena.isEmpty()) {
            _passwordError.value = "La contraseña es obligatoria"
            valido = false
        } else {
            _passwordError.value = ""
        }

        return valido
    }


    sealed class NavigationTarget{
        object Registro : NavigationTarget()
        object Dashboard : NavigationTarget()
    }
    private fun isValidPassword(password:String):Boolean = password.length > 2
    private fun isValidEmail(email:String): Boolean = Patterns.EMAIL_ADDRESS.matcher(email).matches()

}