package com.example.examplemvvm.ui.screens.historial

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel

import com.example.examplemvvm.ui.screens.componentes.Container
import com.example.examplemvvm.data.remote.response.HorarioItem
import com.example.examplemvvm.R

@Composable
fun RecomendacionScreen(
    viewModel: RecomendacionViewModel = hiltViewModel(),
    goToDashboard: () -> Unit
) {
    // escuchar navegación
    LaunchedEffect(Unit) {
        viewModel.navigationEvent.collect { event ->
            when (event) {
                is RecomendacionViewModel.NavigationTarget.goToBack -> goToDashboard()
            }
        }
    }

    // disparar carga de horarios al entrar
    LaunchedEffect(Unit) {
        viewModel.onEvent(RecomendacionEvent.CargarHorarios)
        // el idUsuario ya lo obtiene internamente del SessionManager,
        // el parámetro aquí no se usa realmente, pero mantenemos la llamada
    }

    // estados del ViewModel
    val horarios by viewModel.horarios.collectAsState()
    val mensaje by viewModel.mensaje.collectAsState()

    Container(
        showBackButton = true,
        showEncabezado = true,
        onBackClick = { viewModel.onEvent(RecomendacionEvent.btnBackClicked) },
        encabezado = "Historial"
    ) {
        Historial(horarios, mensaje, onCloseMessage = { viewModel.clearMessage() })
    }
}

@Composable
fun Historial(horarios: List<HorarioItem>, mensaje: UiMessage?,onCloseMessage: () -> Unit

) {
    Column(modifier = Modifier.fillMaxSize()) {
        GeneralLbl("Recomendaciones:",
            tamano = 25.sp,
            alineacionTexto = TextAlign.Center,
            modifier = Modifier.padding(vertical = 20.dp)
        )

        mensaje?.let { msg ->
            val isError = msg.type == UiMessageType.ERROR

            val bgColor = if (isError) Color(0xFFFFCDD2) else MaterialTheme.colorScheme.primaryContainer
            val textColor = if (isError) Color(0xFFB71C1C) else MaterialTheme.colorScheme.onPrimaryContainer

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                colors = CardDefaults.cardColors(containerColor = bgColor)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = msg.text,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Start,
                        color = textColor,
                        modifier = Modifier.weight(1f) // ocupa espacio restante
                    )

                    IconButton(onCloseMessage){
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Cerrar",
                            tint = textColor
                        )
                    }
                }
            }
        }
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(horarios) { horario ->
                Log.d("SugerenciasScreenhora", "Horario recibido: ${horario.hora}")

                val horaDecimal = horario.hora * 24
                val hora = horaDecimal.toInt()
                val minuto = ((horaDecimal - hora) * 60).toInt()
                val horaLegible = "%02d:%02d".format(hora, minuto)

                val estadoTexto = estadoAnimoTexto(horario.estadoAnimo)

                // Seleccionar imagen según estado de ánimo
                val iconRes = when (horario.estadoAnimo.toInt()) {
                    1 -> R.drawable.sadface     // Muy mal
                    2 -> R.drawable.sad         // Mal
                    3 -> R.drawable.confused    // Neutro
                    4 -> R.drawable.happy       // Bien
                    5 -> R.drawable.happyface   // Muy bien
                    else -> R.drawable.confused // Fallback
                }
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(
                                text = "Mejor hora para respirar",
                                style = MaterialTheme.typography.titleMedium
                            )
                            Text(
                                text = "Hora: $horaLegible",
                                style = MaterialTheme.typography.bodyMedium
                            )
                            /*Text(
                                text = "Estado ánimo esperado: $estadoTexto",
                                style = MaterialTheme.typography.bodyMedium
                            )*/
                        }

                        Icon(
                            painter = painterResource(id = R.drawable.respira),
                            contentDescription = "Estado de ánimo",
                            modifier = Modifier.size(55.dp),
                            tint = Color.Unspecified
                        )
                    }
                }
            }
        }
    }
}

fun Double.toHoraLegible(): String {
    val totalMinutos = (this * 24 * 60).toInt()
    val hora = totalMinutos / 60
    val minuto = totalMinutos % 60
    return "%02d:%02d".format(hora, minuto)
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

fun estadoAnimoTexto(valor: Double): String {
    return when (valor.toInt()) {
        1 -> "Muy mal"
        2 -> "Mal"
        3 -> "Neutro"
        4 -> "Bien"
        5 -> "Muy bien"
        else -> "Desconocido"
    }
}


