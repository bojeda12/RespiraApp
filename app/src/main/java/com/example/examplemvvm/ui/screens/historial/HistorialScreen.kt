package com.example.examplemvvm.ui.screens.historial

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.examplemvvm.ui.screens.componentes.Container
import com.example.examplemvvm.ui.screens.componentes.Grafica


@Composable
fun HistorialScreen(
    viewModel: HistorialViewModel = HistorialViewModel(),
    goToDashboard: () -> Unit
) {
    LaunchedEffect(Unit) {
        viewModel.navigationEvent.collect { event ->
            when (event) {
                is HistorialViewModel.NavigationTarget.goToBack->goToDashboard()
            }
        }
    }
    Container(
        showBackButton = true,
        showEncabezado = true,
        onBackClick = {viewModel.onEvent(HistorialEvent.btnBackClicked)},
        encabezado = "Historial"
    ) {
        Historial()
    }
}

@Composable
fun Historial() {

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        GeneralLbl("Estados de animo:", tamano = 20.sp)
        Grafica(moodsByDay = listOf(1, 2, 3, 4, 1))
        GeneralLbl("Tipos de respiracion mas usadas:", tamano = 20.sp)
        Grafica(moodsByDay = listOf(1, 2, 3, 3, 1, 1))
        GeneralLbl("Promedio del estado de animo:", tamano = 20.sp)
        GeneralLbl("Bien \uD83D\uDE01", tamano = 16.sp, alineacionTexto = TextAlign.Center)
        GeneralLbl("Rutinas completadas:", tamano = 20.sp)
        GeneralLbl("2", tamano = 16.sp, alineacionTexto = TextAlign.Center)
        GeneralLbl("Recomendaciones:", tamano = 20.sp)
        GeneralLbl(
            "Se recomienda realizar una rutina de respiracion en un horario de 6:00PM a 8:00PM",
            tamano = 16.sp,
            alineacionTexto = TextAlign.Center
        )

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