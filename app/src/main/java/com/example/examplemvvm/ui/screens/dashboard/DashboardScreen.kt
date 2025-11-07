package com.example.examplemvvm.ui.screens.dashboard

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import com.example.examplemvvm.R
import com.example.examplemvvm.ui.screens.componentes.Container
import com.example.examplemvvm.ui.screens.componentes.Grafica
import com.example.examplemvvm.ui.screens.registro.RegistroViewModel


@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel = hiltViewModel(),
    navegarToConfiguracion: () -> Unit,
    navegarToEstados: () -> Unit,
    navegarToRespirarRutinas: () -> Unit,
    navegarToRespirar: () -> Unit,
    navegarToHistorial: () -> Unit
) {
    // Escuchar eventos de navegación
    LaunchedEffect(Unit) {
        viewModel.navigationEvent.collect { event ->
            when (event) {
                is DashboardViewModel.NavigationTarget.Configuracion -> navegarToConfiguracion()
                is DashboardViewModel.NavigationTarget.Estados -> navegarToEstados()
                is DashboardViewModel.NavigationTarget.RespiracionRutina -> navegarToRespirarRutinas()
                is DashboardViewModel.NavigationTarget.Rutina1 -> navegarToRespirar()
                is DashboardViewModel.NavigationTarget.Historial -> navegarToHistorial()
            }
        }
    }
    Container(
        showConfiguracion = true,
        showEncabezado = true,
        onHomeClick = { viewModel.onEvent(DashboardEvent.BtnConfiguracionClicked) },
        encabezado = "DASHBOARD"
    ) {
        Dashboard(
            modifier = Modifier, viewModel = viewModel,
            navegarToEstados = navegarToEstados,
            navegarToRespirarRutinas = navegarToRespirarRutinas,
            navegarToRespirar = navegarToRespirar,
            navegarToHistorial = navegarToHistorial
        )
    }
}

@Composable
fun Dashboard(
    modifier: Modifier, viewModel: DashboardViewModel,
    navegarToEstados: () -> Unit,
    navegarToRespirarRutinas: () -> Unit,
    navegarToRespirar: () -> Unit,
    navegarToHistorial: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val nombre by viewModel.nombreUsuario.collectAsState()

        BienvenidaLabel(nombre)
        Etiquetas(
            "Ultimo estado de animo registrado: \uD83D\uDE01",
            modifier = Modifier,
            tamanoLetra = 12.sp
        )
        Spacer(Modifier.height(20.dp))
        BotonBox(
            modifier = Modifier,
            "Registra tu animo",
            painterResource(id = R.drawable.mood)
        ){viewModel.onEvent(DashboardEvent.BntEstadoClicked)}
        Spacer(Modifier.height(15.dp))
        BotonBox(modifier = Modifier,
            "Elegir Rutina",
            painterResource(id = R.drawable.rutina)
        ){viewModel.onEvent(DashboardEvent.BtnRespiracionRutinaClicked)}
        Etiquetas(
            texto = "Rutinas de respiracion rapidas",
            modifier = Modifier,
            tamanoLetra = 18.sp
        )
        RutinasRapidas(){viewModel.onEvent(DashboardEvent.BtnRutina1Clicked)}
        Etiquetas(texto = "Historial", modifier = Modifier.clickable {viewModel.onEvent(DashboardEvent.BtnHistorialClicked)}, tamanoLetra = 18.sp)
        Spacer(Modifier.height(30.dp))
        Grafica(moodsByDay = listOf(1, 2, 2, 3, 3, 3, 5))
        Etiquetas(
            texto = "Horario recomendado para respirar: 6:00 PM",
            modifier = Modifier,
            tamanoLetra = 18.sp
        )
    }


}


@Composable
fun BienvenidaLabel(nombreUser: String) {
    Text(
        text = "Bienvenido $nombreUser",
        fontSize = 30.sp,
        modifier = Modifier.padding(top = 20.dp)
    )
}

@Composable
fun Etiquetas(texto: String, modifier: Modifier, tamanoLetra: TextUnit) {
    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.TopStart
    ) {
        Text(
            text = texto,
            fontSize = tamanoLetra,
            modifier = Modifier.padding(top = 20.dp)
        )
    }
}

@Composable
fun DashboardBotones(texto: String) {
    Button(
        onClick = { },
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
        Text(text = texto)
    }
}

@Composable
fun BotonBox(modifier: Modifier, texto: String, dibujo: Painter,onClick: () -> Unit) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(80.dp)
            .clip(shape = RoundedCornerShape(35))
            .background(Color(0xFF359B94))
            .clickable { onClick() },
    ) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 22.dp, start = 20.dp),
            contentAlignment = Alignment.TopStart
        ) {
            Image(
                //painterResource(id = R.drawable.respira)
                modifier = Modifier.size(34.dp),
                painter = dibujo,
                contentDescription = ""
            )
        }
        Box(
            modifier
                .align(Alignment.Center),
            contentAlignment = Alignment.Center
        ) {
            Text(
                texto,
                color = Color.White,
                fontSize = 18.sp
            )
        }

    }
}


@Composable
fun RutinasRapidas(onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .padding(top = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        FloatingActionButtonExample("1 min",onClick)
        FloatingActionButtonExample("3 min",onClick)
        FloatingActionButtonExample("5 min",onClick)
    }
}

@Composable
fun FloatingActionButtonExample(texto: String,onClick: () -> Unit) {
    FloatingActionButton(
        onClick = {onClick()},
        modifier = Modifier
            .width(90.dp)
    ) {
        Text(texto)
    }
}

@Composable
fun grafica(modifier: Modifier) {

}

