package com.example.reloj.presentation.ui

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.lazy.items
import com.example.reloj.presentation.sync.SugerenciasReceiver
import com.example.reloj.R

@Composable
fun SugerenciasScreen() {
    val horarios by SugerenciasReceiver.horariosRecibidos.collectAsState()
    Log.d("SugerenciasScreen", "Horarios observados: ${horarios.size}")

    if (horarios.isEmpty()) {
        // Fallback cuando no hay datos o no está vinculado al teléfono
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "No se pudo establecer conexión con el teléfono",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.error,
                textAlign = TextAlign.Center
            )
        }
    } else {
        ScalingLazyColumn(
            modifier = Modifier.fillMaxSize().padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(horarios.take(5)) { horario ->
                val horaLegible = horario.hora.toHoraLegible()
                val estadoTexto = estadoAnimoTexto(horario.estadoAnimo)
                val emoji = when (horario.estadoAnimo.toInt()) {
                    1 -> "😭"
                    2 -> "🥲"
                    3 -> "🙂"
                    4 -> "😊"
                    5 -> "😄"
                    else -> "🙂"
                }

                Card(modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFB2D8B5),
                    contentColor = Color.Black
                )

                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text("Hora recomendada de sesion\n $horaLegible", textAlign = TextAlign.Center)
                            //Text("Estado: $estadoTexto")
                        }
                        Icon(
                            painter = painterResource(id = R.drawable.respira),
                            contentDescription = "Estado de ánimo",
                            modifier = Modifier.size(50.dp),
                            tint = Color.Unspecified
                        )
                    }
                }
            }
        }
    }
}

//Helper para formatear la hora correctamente
fun Double.toHoraLegible(): String {
    val horaDecimal = this * 24
    val hora = horaDecimal.toInt()
    val minuto = ((horaDecimal - hora) * 60).toInt()
    return "%02d:%02d".format(hora, minuto)
}

// Helper para traducir estado de ánimo
fun estadoAnimoTexto(valor: Double): String = when (valor.toInt()) {
    1 -> "Muy mal"
    2 -> "Mal"
    3 -> "Neutro"
    4 -> "Bien"
    5 -> "Muy bien"
    else -> "Desconocido"
}