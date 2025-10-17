package com.example.examplemvvm.ui.theme.login.ui.screens.login

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

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
class LoginViewModel: ViewModel() {
    private val _state = MutableLiveData(LoginState())
    val state: LiveData<LoginState> = _state

    fun onEvent(event: LoginEvent){
        val current = _state.value ?: LoginState()
        when(event){
            is LoginEvent.EmailChanged -> {
                val newEmail = event.email
                val isValid = isValidEmail(newEmail) && isValidPassword(current.password)
                _state.value = current.copy(email=newEmail,isLoginEnabled = isValid)
            }

            is LoginEvent.PasswordChanged -> {
                val newPassword = event.password
                val isValid = isValidEmail(current.email) && isValidPassword(newPassword)
                _state.value = current.copy(password = newPassword,isLoginEnabled = isValid)
            }

            is LoginEvent.LoginClicked -> {

            }
        }

    }
    private fun isValidPassword(password:String):Boolean = password.length > 6
    private fun isValidEmail(email:String): Boolean = Patterns.EMAIL_ADDRESS.matcher(email).matches()
    /*
    //Email
    private val _email = MutableLiveData<String>()
    val email : LiveData<String> = _email

    //Password

    private val _password = MutableLiveData<String>()
    val password : LiveData<String> = _password

    //Valor que nos ayudara a habilitar o deshabilitar el boton del login
    private val _loginEnable = MutableLiveData<Boolean>()
    val loginEnable : LiveData<Boolean> = _loginEnable


    fun onLoginChange(email: String,password: String) {
        _email.value = email
        _password.value = password
        _loginEnable.value = isValidEmail(email) && isValidPassword(password)
    }

    private fun isValidPassword(password: String):Boolean = password.length > 6

    private fun isValidEmail(email: String):Boolean = Patterns.EMAIL_ADDRESS.matcher(email).matches()

    fun onLoginSelected() {

    }*/

}