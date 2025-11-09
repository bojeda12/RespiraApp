package com.example.examplemvvm.ui.screens.registro

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.examplemvvm.R
import com.example.examplemvvm.ui.components.AlertaSnackBar
import com.example.examplemvvm.ui.components.AlertaTipo
import com.example.examplemvvm.ui.screens.componentes.Container
import com.example.examplemvvm.ui.screens.componentes.TimePickerGenerico
import com.example.examplemvvm.ui.screens.componentes.Logo
import com.example.examplemvvm.ui.screens.componentes.TxtPasswordField
import com.example.examplemvvm.ui.screens.componentes.TxtFieldGeneral
import com.example.examplemvvm.ui.screens.estado.BotonEstados
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun RegistroScreen(
    viewModel: RegistroViewModel = hiltViewModel(),
    navegarToDashboard: () -> Unit,
    navegarToLogin: () -> Unit
) {
    val state by viewModel.state.observeAsState(RegistroState())
    val coroutineScope = rememberCoroutineScope()
    var currentStep by remember { mutableStateOf(1) }
    var alerta by remember { mutableStateOf<Pair<String, AlertaTipo>?>(null) }

    // Escuchar mensajes del ViewModel
    LaunchedEffect(Unit) {
        viewModel.mensajeUI.collect { mensaje ->
            alerta = mensaje
        }
    }

    // Escuchar navegación
    LaunchedEffect(Unit) {
        viewModel.navigationEvent.collect { target ->
            when (target) {
                is RegistroViewModel.NavigationTarget.Dashboard -> navegarToDashboard()
                is RegistroViewModel.NavigationTarget.Login -> navegarToLogin()
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Container(
            showBackButton = true,
            onBackClick = { viewModel.onEvent(RegistroEvent.BackClicked) },
            showEncabezado = true,
            encabezado = "REGÍSTRATE"
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                when (currentStep) {
                    1 -> StepDatosUsuario(state, viewModel, coroutineScope) { currentStep++ }
                    2 -> StepRegistroAnimo(state, viewModel, coroutineScope) {
                        coroutineScope.launch {
                            if (viewModel.validarStep2()) {
                                currentStep++
                            }
                        }
                    }

                    3 -> StepRegistroHorario(state, viewModel, coroutineScope) {
                        coroutineScope.launch {
                            if (viewModel.validarStep3()) {
                                viewModel.onEvent(RegistroEvent.RegistroClicked)
                            }
                        }
                    }
                }

                if (currentStep > 1) {
                    Spacer(modifier = Modifier.height(20.dp))
                    TextButton(onClick = { currentStep-- }) {
                        Text("Regresar")
                    }
                }
            }
        }

        // Snackbar personalizado
        alerta?.let { (msg, tipo) ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentAlignment = Alignment.BottomCenter
            ) {
                AlertaSnackBar(
                    message = msg,
                    type = tipo,
                    onDismiss = { alerta = null },
                    modifier = Modifier.zIndex(1f)
                )
            }
        }
    }
}

@Composable
fun StepDatosUsuario(
    state: RegistroState,
    viewModel: RegistroViewModel,
    coroutineScope: CoroutineScope,
    onNext: () -> Unit
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text("Paso 1: Información básica", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(16.dp))
        Logo(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            imagen = painterResource(id = R.drawable.respira)
        )

        TxtFieldGeneral(state.nombreUsuario, "Nombre de usuario", "Ejemplo: pepe") {
            viewModel.onEvent(RegistroEvent.nombreUsuarioChanged(it))
        }

        TxtFieldGeneral(state.correo, "Correo", "Ejemplo: pepe@gmail.com") {
            viewModel.onEvent(RegistroEvent.correoChanged(it))
        }

        TxtPasswordField(state.contrasena, "Contrasena", "Ingresa tu contrasena") {
            viewModel.onEvent(RegistroEvent.contrasenaChanged(it))
        }

        TxtPasswordField(
            state.confirmarContrasena,
            "Confirma contrasena",
            "Confirma tu contrasena"
        ) {
            viewModel.onEvent(RegistroEvent.confirmarContrasenaChanged(it))
        }

        Spacer(Modifier.height(24.dp))
        Button(onClick = {
            coroutineScope.launch {
                if (viewModel.validarStep1()) {
                    onNext()
                }
            }
        }, modifier = Modifier.fillMaxWidth()) {
            Text("Siguiente")
        }
    }
}

@Composable
fun StepRegistroAnimo(
    state: RegistroState,
    viewModel: RegistroViewModel,
    coroutineScope: CoroutineScope,
    onNext: () -> Unit
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text("Paso 2: ¿Cómo te sientes hoy?", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(16.dp))

        Column(
            modifier = Modifier
                .padding(top = 50.dp)
                .height(420.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            val opciones = listOf(
                "Muy bien" to "1",
                "Bien" to "2",
                "Neutro" to "3",
                "Mal" to "4",
                "Muy mal" to "5"
            )

            opciones.forEach { (texto, valor) ->
                val icono = when (valor) {
                    "1" -> painterResource(id = R.drawable.happyface)
                    "2" -> painterResource(id = R.drawable.happy)
                    "3" -> painterResource(id = R.drawable.confused)
                    "4" -> painterResource(id = R.drawable.sad)
                    else -> painterResource(id = R.drawable.sadface)
                }

                val seleccionado = state.estadoAnimo == valor
                BotonEstados(estado = texto, dibuja = icono, isSelected = seleccionado) {
                    viewModel.onEvent(RegistroEvent.estadoAnimoChanged(valor))
                }
            }
        }

        Spacer(Modifier.height(24.dp))
        Button(
            onClick = { if (viewModel.validarStep2()) onNext() },
            modifier = Modifier.fillMaxWidth(),
            enabled = state.estadoAnimo.isNotEmpty()
        ) {
            Text("Siguiente")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StepRegistroHorario(
    state: RegistroState,
    viewModel: RegistroViewModel,
    coroutineScope: CoroutineScope,
    onRegister: () -> Unit
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text("Paso 3:Horario de sesion", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(16.dp))
        TimePickerGenerico(
            onTimeSelected = { hour,minute->
            viewModel.onEvent(RegistroEvent.horaRespiracionChanged(hour))
            viewModel.onEvent(RegistroEvent.minutoRespiracionchanged(minute))
        })
        //MyTimePicker(viewModel = viewModel)
        Spacer(Modifier.height(24.dp))
        Button(
            onClick = onRegister,
            modifier = Modifier.fillMaxWidth(),
            enabled = state.isFormularioValido()
        ) {
            Text("Registrar usuario")
        }
    }
}
