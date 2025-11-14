package com.example.examplemvvm.ui.screens.estado

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.examplemvvm.R
import com.example.examplemvvm.ui.screens.componentes.Container

@Composable
fun EstadoScreen(
    viewModel: EstadoViewModel = hiltViewModel(),
    navegarToDashboard: () -> Unit
) {
    LaunchedEffect(Unit) {
        viewModel.navigationEvent.collect { event ->
            when (event) {
                is EstadoViewModel.NavigationTarget.ToBack-> navegarToDashboard()
            }
        }
    }

    Container(
        showEncabezado = true,
        showBackButton = true,
        onBackClick = { viewModel.onEvent(EstadoEvent.btnBackClicked) },
        encabezado = "ELIGE ESTADO DE ÁNIMO"
    ) {
        Estados(viewModel)
    }
}

@Composable
fun Estados(viewModel: EstadoViewModel) {
    val opciones = listOf(
        "Muy bien" to "1",
        "Bien" to "2",
        "Neutro" to "3",
        "Mal" to "4",
        "Muy mal" to "5"
    )

    Column(
        modifier = Modifier
            .padding(top = 50.dp)
            .height(550.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        LblEstado()
        opciones.forEach { (texto, iconoRes) ->
            val icono = when (iconoRes) {
                "1" -> painterResource(id = R.drawable.happyface)
                "2" -> painterResource(id = R.drawable.happy)
                "3" -> painterResource(id = R.drawable.confused)
                "4" -> painterResource(id = R.drawable.sad)
                else -> painterResource(id = R.drawable.sadface)
            }
            BotonEstados(
                estado = texto,
                dibuja = icono
            )
            {
                viewModel.onEvent(EstadoEvent.EstadoSeleccionado(iconoRes))
            }
        }
    }
}

@Composable
fun LblEstado() {
    Text(
        text = "¿Cómo te sientes hoy?",
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center,
        fontSize = 30.sp
    )
}

@Composable
fun BotonEstados(
    modifier: Modifier = Modifier,
    estado: String,
    dibuja: Painter,
    isSelected: Boolean = false,
    onClick: () -> Unit
) {
    val backgroundColor = if (isSelected) Color(0xFF347771) else Color(0xFF359B94)
    val textColor = Color.White

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(80.dp)
            .clip(RoundedCornerShape(35.dp))
            .background(backgroundColor)
            .clickable { onClick() }
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 22.dp, start = 20.dp),
            contentAlignment = Alignment.TopStart
        ) {
            Image(
                modifier = Modifier.size(34.dp),
                painter = dibuja,
                contentDescription = estado
            )
        }

        Box(
            modifier = Modifier.align(Alignment.Center),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = estado,
                color = textColor,
                fontSize = 18.sp,
                modifier = Modifier.width(180.dp),
                textAlign = TextAlign.Center,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}