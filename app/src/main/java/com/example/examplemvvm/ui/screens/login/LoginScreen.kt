package com.example.examplemvvm.ui.theme.login.ui.screens.login

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.examplemvvm.ui.screens.componentes.Container
import com.example.examplemvvm.ui.screens.componentes.Logo
import com.example.examplemvvm.ui.screens.componentes.TextFields
import com.example.examplemvvm.R
import com.example.examplemvvm.ui.components.AlertaSnackBar

/*
* Creamos el vie model en el login screen y podemos pasar esos parametros en donde los vayamos a utilziar
* */


@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    navegarRegistro: () -> Unit,
    navegarDashboard: () -> Unit
) {
    /*Definimos el view model en la raiz de todos los componentes ya que de aqui
    parte o heredan los demas esta caracateristica del view model, tambien se instancia o inicializa
    aqui adentro para no tener que inicializarlo en el mainActivity*/

    // usamos solo el Container reutilizable para la "card" blanca
    Box(modifier = Modifier.fillMaxSize()) {
        Container() {
            // aqui estamos en el ColumnScope interno del Container
            // llama al composable que contiene los campos y botones
            Login(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                viewModel = viewModel,
                navegarRegistro = navegarRegistro,
                navegarDashboard = navegarDashboard
            )
        }

    }
}

@Composable
fun Login(
    modifier: Modifier,
    viewModel: LoginViewModel,
    navegarRegistro: () -> Unit,
    navegarDashboard: () -> Unit
) {
    // Escuchar eventos de navegación
    LaunchedEffect(Unit) {
        viewModel.navigationEvent.collect { event ->
            Log.d("LoginScreen", "Evento recibido: $event")
            when (event) {
                is LoginViewModel.NavigationTarget.Registro -> navegarRegistro()
                is LoginViewModel.NavigationTarget.Dashboard -> navegarDashboard()
            }
        }
    }

    val state by viewModel.state.observeAsState(LoginState())

    //variables que tran los errores del viewModel
    val emailError by viewModel.emailError
    val passwordError by viewModel.passwordError
    //val email : String by viewModel.email.observeAsState(initial = "")//Cremaos el evento que observa los cambios con el observer state
    // val password : String by viewModel.password.observeAsState(initial = "")
    //val loginEnable : Boolean by viewModel.loginEnable.observeAsState(initial = false)

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        Logo(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            imagen = painterResource(id = R.drawable.respira1)
        )
        Spacer(modifier = Modifier.padding(10.dp))
        //EmailField(state.email) { viewModel.onEvent(LoginEvent.EmailChanged(it)) }
        TextFields(state.email, "Email", "Email", errorMessage = emailError) { viewModel.onEvent(LoginEvent.EmailChanged(it)) }
        Spacer(modifier = Modifier.padding(18.dp))
        PasswordField(state.password, errorMessage = passwordError) { viewModel.onEvent(LoginEvent.PasswordChanged(it)) }
        Spacer(modifier = Modifier.padding(4.dp))
        ForgotPassword(Modifier.align(Alignment.End))
        Spacer(modifier = Modifier.padding(10.dp))
        LoginButton() {
            Log.d("LoginScreen", "Botón presionado")
            viewModel.onEvent(LoginEvent.LoginClicked)
        }
        Spacer(modifier = Modifier.padding(4.dp))
        Registrate(modifier = Modifier) { viewModel.onEvent(LoginEvent.RegistrateClicked) }
    }
}

@Composable
fun Registrate(modifier: Modifier, onClick: () -> Unit) {
    Text(
        text = "¿No tienes una cuenta?, registrte ahora",
        modifier = modifier.clickable { onClick() },
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold,
        color = Color(0xFF41837B),
    )
}


@Composable
fun LoginButton(onLoginSelected: () -> Unit) {
    Button(
        onClick = { onLoginSelected() },
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        colors = ButtonDefaults.buttonColors
            (
            containerColor = Color(0xFF359B94),
            disabledContainerColor = Color(0xFF347771),
            contentColor = Color(0xFFFFFFFF),
            disabledContentColor = Color(0xFFFFFFFF)
        )
    )
    {
        Text(text = "Iniciar sesion")
    }
}

@Composable
fun ForgotPassword(modifier: Modifier) {
    Text(
        text = "Olvidaste la contrasena",
        modifier = modifier.clickable {},
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold,
        color = Color(0xFF41837B)
    )
}

@Composable//brandon
fun PasswordField(password: String,errorMessage: String = "", onTextFieldChanged: (String) -> Unit) {
    Column(modifier = Modifier.fillMaxWidth()) {
        TextField(
            value = password,
            onValueChange = { onTextFieldChanged(it) },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Password") },
            placeholder = { Text(text = "Password") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            singleLine = true,
            maxLines = 1,
            colors = TextFieldDefaults.colors(
                focusedTextColor = Color(0xFF41837B),
                unfocusedTextColor = Color(0xFF57C4BC),
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
            )
        )
        if (errorMessage.isNotEmpty()) {
            Text(
                text = errorMessage,
                color = Color.Red,
                fontSize = 12.sp,
                modifier = Modifier.padding(start = 16.dp, top = 2.dp)
            )
        }
    }
}
