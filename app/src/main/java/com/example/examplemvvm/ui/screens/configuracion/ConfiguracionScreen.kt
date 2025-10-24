package com.example.examplemvvm.ui.screens.configuracion

import android.widget.Space
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.examplemvvm.ui.screens.componentes.Container
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.TimePickerLayoutType
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import com.example.examplemvvm.ui.screens.componentes.TextFieldCreated1
import java.util.Calendar


@Preview
@Composable
fun ConfiguracionScreen() {
    Configuracion()
}

@Composable
fun Configuracion() {
    Container(
        showEncabezado = true,
        showBackButton = true,
        encabezado = "Configuracion",
        onBackClick = {}
    ) {
        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                GeneralLbl(texto = "Activar notificaciones", tamano = 18.sp)
                EstadoChange()
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                GeneralLbl(texto = "Activar recomendaciones", tamano = 18.sp)
                EstadoChange()
            }
            GeneralLbl(
                texto = "Selecciona un hoario que deses para tus rutinas",
                tamano = 18.sp,
                alineacionTexto = TextAlign.Center
            )
            SeleccionHora()
            Box(
                modifier = Modifier
                    .padding(top = 10.dp, bottom = 15.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10))
                    .background(Color(0x5C359B94))

            ) {
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp, vertical = 15.dp)
                ) {
                        GeneralLbl(texto = "Modificar Perfil", tamano = 20.sp, modifier = Modifier.fillMaxWidth(), alineacionTexto = TextAlign.Center)
                    TextFieldCreated1(

                        valor = "",
                        etiqueta = "Usuario",
                        placeholderTexto = "Ejemplo:user123",
                    ) {}
                    TextFieldCreated1(
                        valor = "",
                        etiqueta = "Correo",
                        placeholderTexto = "Ejemplo@gmail.com",
                    ) {}
                    TextFieldCreated1(
                        valor = "",
                        etiqueta = "Contrasena",
                        placeholderTexto = "Ejemplo:Ejemplo123!",
                    ) {}
                    Spacer(Modifier.height(15.dp))
                    BotonBox(modifier = Modifier, "Guardar", background = 0xFF41837B)
                }

            }
            BotonBox(modifier = Modifier, texto = "Cerrar sesion", background = 0xFFB70000)
            Spacer(Modifier.height(35.dp))
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
        modifier = modifier.padding(top = 10.dp, bottom = 5.dp),
        fontSize = tamano,
        textAlign = alineacionTexto
    )
}

@Composable
fun EstadoChange() {
    val checkedState = remember { mutableStateOf(true) }
    Switch(
        checked = checkedState.value,
        onCheckedChange = { checkedState.value = it }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SeleccionHora() {
    val state = rememberTimePickerState()
    TimePicker(
        state = state,
        modifier = Modifier
            .padding(15.dp)
            .fillMaxWidth(),
        colors = TimePickerDefaults.colors(),
        layoutType = TimePickerDefaults.layoutType()
    )
    Text(
        text = "Hora seleccionada H:M = ${state.hour} : ${state.minute}",
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center
    )
}

@Composable
fun BotonBox(modifier: Modifier, texto: String,background: Long) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp)
            .clip(shape = RoundedCornerShape(40))
            .background(Color(background))
            .clickable { print("holo") },
    ) {
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





