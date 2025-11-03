package com.example.examplemvvm.ui.screens.rutinas

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.examplemvvm.R
import com.example.examplemvvm.ui.screens.componentes.Container

@Composable
fun RutinaScreen(
    viewModel: RutinaViewModel = RutinaViewModel(),
    navegarToRespira: () -> Unit,
    navegarBackDashboard: () -> Unit
) {
    //Escuchar los elementos de navegacion
    LaunchedEffect(Unit) {
        viewModel.navigationEvent.collect { event ->
            when (event) {
                is RutinaViewModel.NavigationTarget.toRespira -> navegarToRespira()
                is RutinaViewModel.NavigationTarget.toDashboard -> navegarBackDashboard()
            }
        }
    }
    Container(
        showBackButton = true,
        onBackClick = { viewModel.onEvent(RutinaEvent.btnBackDasboardClicked) },
        showEncabezado = true,
        encabezado = "Rutinas de respiracion"
    ) {
        Rutina(
            modifier = Modifier,
            viewModel = viewModel,
            navegarToRespira = navegarToRespira
        )
    }

}

@Composable
fun Rutina(
    modifier: Modifier,
    viewModel: RutinaViewModel,
    navegarToRespira: () -> Unit
) {
    val openDialog = remember { mutableStateOf(false) }
    val rutinaSeleccionada = remember { mutableStateOf<RutinaInfo?>(null) }
    val rutinas = listOf(
        RutinaInfo(
            "Respiracion Profunda",
            "¿Como practicarla?",
            "Esta es una de las técnicas de respiración más sencillas, ideales para aplicar en cualquier momento y lugar. Su función es la de tranquilizarnos cuando estamos estresados, aunque la podemos usar sin necesidad de estar alterados. La cuestión es que sirve para inducir un estado anímico calmado y relajado.\n" +
                    "Consiste en tomar aire por la nariz durante unos 4 segundos. Lo mantenemos en los pulmones mientras contamos hasta 4 mentalmente y con mucha calma. Pasado ese tiempo, procedemos a soltar el aire con tranquilidad durante otros 4 segundos. Repetimos tantas veces como nos sea necesario, aunque recomendamos unas 5 o 6.",
            rutinaClicked = { viewModel.onEvent(RutinaEvent.btnRespiracionProfundaClicked) }
        ),
        RutinaInfo(
            "Respiracion completa",
            "¿Como practicarla?",
            "En este ejercicio se aplica la respiración abdominal, que es profunda.\n" +
                    "Primero expulsamos todo el aire de los pulmones, haciendo que queden bien vacíos. Después, procedemos a inspirar suave y profundamente, con el fin de llenar al máximo el abdomen, seguido de los pulmones y el pecho. Mantenemos el aire unos 4 segundos y lo expulsamos lentamente, notando como se vacía primero el tórax y después el abdomen.",
            rutinaClicked = { viewModel.onEvent(RutinaEvent.btnREspiracionCompletaClicked) }
        ),
        RutinaInfo(
            "Respiracion para dormir mejor",
            "¿Como practicarla?",
            "Este ejercicio de respiración consciente nos servirá para controlar el estrés y, consecuentemente, dormiremos mejor. Colocamos la punta de la lengua en el paladar, justo detrás de los incisivos superiores. Inhalaremos por la nariz durante unos 4 segundos, mantendremos la respiración entre 6 y 8 segundos.\n" +
                    "Una vez pasadas estas dos primeras partes, exhalamos por la boca frunciendo los labios y haciendo ruido, soplando, notando como liberamos toda nuestra tensión interna, durante unos 8 segundos. Repetiremos todo el ejercicio unas cuatro veces más.",
            rutinaClicked = { viewModel.onEvent(RutinaEvent.btnRespiracionDormirMejorClicked) }
        ),
        RutinaInfo(
            "Respiracion de la abeja",
            "¿Como practicarla?",
            "A continuación, se inhala profundamente por la nariz y, al exhalar, se emite el zumbido con la boca cerrada, imitando el sonido de una abeja. Esta respiración debe repetirse por unos diez minutos para notar sus efectos.",
            rutinaClicked = { viewModel.onEvent(RutinaEvent.btnRespiracionAbejaClicked) }
        )
    )

    Column(
        modifier = Modifier
            .padding(top = 50.dp)
            .height(440.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        LblTitulo()
        rutinas.forEach { rutina ->
            BotonRutinas(
                nombreRutina = rutina.nombre,
                onInfoClick = {
                    rutinaSeleccionada.value = rutina
                    openDialog.value = true
                },
                rutinaClicked = { rutina.rutinaClicked() }
            )
        }
        if (openDialog.value && rutinaSeleccionada.value != null) {
            AlertDialogDoc(
                onDismiss = { openDialog.value = false },
                tipoRutina = rutinaSeleccionada.value!!.tipo,
                descriptionRutina = rutinaSeleccionada.value!!.descripcion
            )
        }

    }

}

@Composable
fun LblTitulo() {
    Text(
        "Elige una tecnica de respiracion",
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center,
        fontSize = 30.sp
    )
}

@Composable
fun BotonRutinas(
    modifier: Modifier = Modifier,
    nombreRutina: String = "",
    onInfoClick: () -> Unit,
    rutinaClicked:() ->Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(80.dp)
            .clip(shape = RoundedCornerShape(35))
            .background(Color(0xFF359B94)).clickable { rutinaClicked() }

    ) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 22.dp, start = 20.dp),
            contentAlignment = Alignment.TopStart
        ) {
            Image(
                //painterResource(id = R.drawable.respira)
                modifier = Modifier
                    .size(34.dp)
                    .clickable { onInfoClick() },
                painter = painterResource(id = R.drawable.information),
                contentDescription = ""
            )
        }
        Box(
            modifier
                .align(Alignment.Center)
                ,
            contentAlignment = Alignment.Center
        ) {
            Text(
                nombreRutina,
                color = Color.White,
                fontSize = 18.sp,
                modifier = modifier.width(180.dp),
                textAlign = TextAlign.Center,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }

    }
}

@Composable
fun AlertDialogDoc(onDismiss: () -> Unit, tipoRutina: String, descriptionRutina: String) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(tipoRutina) },
        text = { Text(descriptionRutina) },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Aceptar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Salir")
            }
        }
    )
}

data class RutinaInfo(
    val nombre: String,
    val tipo: String,
    val descripcion: String,
    val rutinaClicked: () -> Unit
)

