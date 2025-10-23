package com.example.examplemvvm.ui.screens.dashboard

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
import androidx.lifecycle.ViewModel
import com.example.examplemvvm.R
import com.example.examplemvvm.ui.screens.componentes.Container


@Preview(showBackground = true)
@Composable
fun DashboardScreen(viewModel: DashboardViewModel = DashboardViewModel()) {
    Container(
        showConfiguracion = true,
        showEncabezado = true,
        encabezado = "DASHBOARD"
    ) {
        Dashboard(modifier = Modifier, viewModel = viewModel)
    }
}

@Composable
fun Dashboard(modifier: Modifier, viewModel: DashboardViewModel) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BienvenidaLabel("Brandon")
        Etiquetas(
            "Ultimo estado de animo registrado: \uD83D\uDE01",
            modifier = Modifier,
            tamanoLetra = 12.sp
        )
        Spacer(Modifier.height(20.dp))
        BotonBox(
            modifier = Modifier,
            "Registra estado de animo",
            painterResource(id = R.drawable.mood)
        )
        Spacer(Modifier.height(15.dp))
        BotonBox(modifier = Modifier, "Elegir Rutina", painterResource(id = R.drawable.rutina))
        Etiquetas(
            texto = "Rutinas de respiracion rapidas",
            modifier = Modifier,
            tamanoLetra = 18.sp
        )
        RutinasRapidas()
        Etiquetas(texto = "Historial", modifier = Modifier.clickable{}, tamanoLetra = 18.sp)
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
        modifier = Modifier.fillMaxWidth(),
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
fun BotonBox(modifier: Modifier, texto: String, dibujo: Painter) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(80.dp)
            .clip(shape = RoundedCornerShape(35))
            .background(Color(0xFF359B94))
            .clickable { print("holo") },
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
fun RutinasRapidas() {
    Row(
        modifier = Modifier.fillMaxWidth()
            .height(70.dp).padding(top = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        FloatingActionButtonExample("1 minutos")
        FloatingActionButtonExample("3 minutos")
        FloatingActionButtonExample("5 minutos")
    }
}

@Composable
fun FloatingActionButtonExample(texto: String) {
    FloatingActionButton(onClick = { },
        modifier = Modifier
            .width(100.dp)) {
        Text(texto)
    }
}

