package com.example.examplemvvm.ui.screens.rutinas

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.examplemvvm.ui.screens.componentes.Container

@Preview
@Composable
fun RutinaScreen() {
    Rutina()
}

@Composable
fun Rutina() {
    Container(
        showBackButton = true,
        onBackClick = {},
        showEncabezado = true,
        encabezado = "Rutinas de respiracion"
    ) {

    }
}

@Composable
fun BtnRutinas(tipoRespiracion: String) {

}