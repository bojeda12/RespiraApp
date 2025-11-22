package com.example.examplemvvm.ui.screens.historial

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.examplemvvm.data.local.session.SesionManager
import com.example.examplemvvm.ui.screens.componentes.Container
import com.example.examplemvvm.data.remote.response.HorarioItem

@Composable
fun RecomendacionScreen(
    viewModel: RecomendacionViewModel = hiltViewModel(),
    goToDashboard: () -> Unit
) {
    // 👉 escuchar navegación
    LaunchedEffect(Unit) {
        viewModel.navigationEvent.collect { event ->
            when (event) {
                is RecomendacionViewModel.NavigationTarget.goToBack -> goToDashboard()
            }
        }
    }

    // 👉 disparar carga de horarios al entrar
    LaunchedEffect(Unit) {
        viewModel.onEvent(RecomendacionEvent.CargarHorarios)
        // 👆 el idUsuario ya lo obtiene internamente del SessionManager,
        // el parámetro aquí no se usa realmente, pero mantenemos la llamada
    }




    // 👉 estados del ViewModel
    val horarios by viewModel.horarios.collectAsState()
    val mensaje by viewModel.mensaje.collectAsState()

    Container(
        showBackButton = true,
        showEncabezado = true,
        onBackClick = { viewModel.onEvent(RecomendacionEvent.btnBackClicked) },
        encabezado = "Historial"
    ) {
        Historial(horarios, mensaje)
    }
}

@Composable
fun Historial(horarios: List<HorarioItem>, mensaje: String?) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        GeneralLbl("Recomendaciones:", tamano = 20.sp)

        // Mostrar mensaje de error si existe
        mensaje?.let {
            GeneralLbl(it, tamano = 16.sp, alineacionTexto = TextAlign.Center)
        }

        // Mostrar lista de horarios recomendados
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(horarios) { horario ->
                val horaLegible =
                    ((horario.hora * 24).toInt()).toString().padStart(2, '0') + ":00"
                GeneralLbl(
                    texto = "Hora: $horaLegible | Estado ánimo: ${horario.estadoAnimo}",
                    tamano = 16.sp,
                    alineacionTexto = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun GeneralLbl(
    texto: String = "",
    modifier: Modifier = Modifier,
    tamano: TextUnit = TextUnit.Unspecified,
    alineacionTexto: TextAlign? = null
) {
    Text(
        texto,
        modifier = modifier
            .padding(top = 10.dp, bottom = 5.dp)
            .fillMaxWidth(),
        fontSize = tamano,
        textAlign = alineacionTexto
    )
}