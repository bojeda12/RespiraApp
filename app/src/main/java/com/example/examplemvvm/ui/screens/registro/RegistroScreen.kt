package com.example.examplemvvm.ui.screens.registro

import android.graphics.drawable.Icon
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.TimePickerColors
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.TimePickerLayoutType
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.examplemvvm.R
import com.example.examplemvvm.ui.screens.componentes.Container
import com.example.examplemvvm.ui.screens.componentes.Logo
import com.example.examplemvvm.ui.screens.componentes.TextFieldCreated1
import com.example.examplemvvm.ui.screens.componentes.TextFields
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.painter.Painter
import com.example.examplemvvm.ui.theme.login.ui.screens.login.LoginViewModel


@Composable
fun RegistroScreen(
    viewModel: RegistroViewModel = RegistroViewModel(),
    navegarToDashboard: () -> Unit,
    navegarToLogin:() -> Unit
) {
    // Escuchar eventos de navegación
    LaunchedEffect(Unit) {
        viewModel.navigationEvent.collect { event ->
            Log.d("LoginScreen", "Evento recibido: $event")
            when (event) {
                is RegistroViewModel.NavigationTarget.Login -> navegarToLogin()
                is RegistroViewModel.NavigationTarget.Dashboard -> navegarToDashboard()
            }
        }
    }
    Container(
        showBackButton = true,
        showHomeButton = false,
        onBackClick = {viewModel.onEvent(RegistroEvent.BackClicked)},
        encabezado = "REGISTRATE",
        showEncabezado = true
    ) {
        Registro(
            modifier = Modifier,
            viewModel = viewModel,
            navegarToDashboard = navegarToDashboard
        )
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Registro(
    modifier: Modifier, viewModel: RegistroViewModel,
    navegarToDashboard: () -> Unit
) {

    val state by viewModel.state.observeAsState(RegistroState())
    Column(
        modifier = modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Logo(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            imagen = painterResource(id = R.drawable.respira)
        )
        TextFieldCreated1(
            state.nombreUsuario,
            "Nombre de usuario",
            "Ejemplo:pepe@gmail.com"
        )
        { viewModel.onEvent(RegistroEvent.NombreUsuarioChanged(it)) }

        TextFieldCreated1(
            state.correo,
            "Correo",
            "Ingresa tu correo"
        ) { viewModel.onEvent(RegistroEvent.correoChanged(it)) }

        TextFieldCreated1(state.contrasena, "Contrasena", "Ingresa tu contrasena") {
            viewModel.onEvent(
                RegistroEvent.contrasenaChanged(it)
            )
        }

        TextFieldCreated1(
            state.confirmarContrasena,
            "Confirma contrasena",
            "Confirma tu contrasena",
        ) { viewModel.onEvent(RegistroEvent.confirmarContrasenaChanged(it)) }
        Spacer(modifier = Modifier.padding(8.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.Start
        ) {
            Label1(modifier = modifier, texto = "¿Como te sintes el dia de hoy?")

        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp)
                .height(60.dp)
                .background(color = Color.White),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            FloatingActionButtonExample(imagen = painterResource(id = R.drawable.sadface))
            FloatingActionButtonExample(imagen = painterResource(id = R.drawable.sad))
            FloatingActionButtonExample(imagen = painterResource(id = R.drawable.confused))
            FloatingActionButtonExample(imagen = painterResource(id = R.drawable.happy))
            FloatingActionButtonExample(imagen = painterResource(id = R.drawable.happyface))
        }
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            Label1(modifier = modifier, texto = "Elige un horario para respirar")

        }
        MyTimePicker()
        Spacer(Modifier.height(30.dp))
        RegistrateButton(){viewModel.onEvent(RegistroEvent.RegistroClicked)}
        Spacer(Modifier.height(30.dp))
    }

}

@Composable
fun Label1(modifier: Modifier, texto: String) {
    Text(
        text = texto,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        color = Color(0xFF347771),

        )
}

@Composable
fun FloatingActionButtonExample(imagen: Painter) {
    FloatingActionButton(onClick = { }) {
        Image(
            painter = imagen,
            contentDescription = "Botón de acción",
            modifier = Modifier.size(24.dp) // ajusta el tamaño del ícono
        )
    }
}

@Composable
@ExperimentalMaterial3Api
fun MyTimePicker() {
    val state = rememberTimePickerState()
    TimePicker(
        state = state,
        modifier = Modifier.padding(15.dp),
        colors = TimePickerDefaults.colors(),
        layoutType = TimePickerDefaults.layoutType()
    )
    Text(text = "Hora seleccionada H:M = ${state.hour} : ${state.minute}")
}

@Composable
fun RegistrateButton(modifier: Modifier = Modifier, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        colors = ButtonDefaults.buttonColors
            (
            containerColor = Color(0xFF359B94),
            disabledContainerColor = Color(0xFF347771),
            contentColor = Color(0xFFFFFFFF),
            disabledContentColor = Color(0xFFFFFFFF)
        ),
    )
    {
        Text(text = "Registrate")
    }
}

