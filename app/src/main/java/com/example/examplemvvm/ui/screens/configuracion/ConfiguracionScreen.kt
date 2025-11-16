package com.example.examplemvvm.ui.screens.configuracion

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.examplemvvm.ui.screens.componentes.Container
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.examplemvvm.ui.components.AlertaSnackBar
import com.example.examplemvvm.ui.components.AlertaTipo
import com.example.examplemvvm.ui.screens.componentes.TxtFieldGeneral
import com.example.examplemvvm.ui.screens.registro.RegistroState


@Composable
fun ConfiguracionScreen(
    viewModel: ConfiguracionViewModel = hiltViewModel(),
    navegarToDashboard: () -> Unit,
    cerrarSesion: () -> Unit
) {

    var alerta by remember { mutableStateOf<Pair<String, AlertaTipo>?>(null) }
    LaunchedEffect(Unit) {
        viewModel.mensajeUI.collect { mensaje ->
            alerta = mensaje
        }
    }


    LaunchedEffect(Unit) {
        viewModel.navigationEvent.collect { event ->
            when (event) {
                is ConfiguracionViewModel.NavigationTarget.goToBack->navegarToDashboard()
                is ConfiguracionViewModel.NavigationTarget.cerrarSesion->cerrarSesion()
            }
        }
    }

    Container(
        showEncabezado = true,
        showBackButton = true,
        encabezado = "Configuracion",
        onBackClick = {viewModel.onEvent(ConfiguracionEvent.btnBackClicked)}
    ) {
        Configuracion(viewModel = viewModel,cierraSesion = cerrarSesion)
    }


    // Snackbar personalizado
    alerta?.let { (msg, tipo) ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            AlertaSnackBar(
                message = msg,
                type = tipo,
                onDismiss = { alerta = null },
                modifier = Modifier.zIndex(1f)
            )
        }
    }
}

@Composable
fun Configuracion(viewModel : ConfiguracionViewModel, cierraSesion:()-> Unit) {
    val state by viewModel.state.observeAsState(ConfiguracionState())
    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
        val estadoNotificaciones by viewModel.notificacionesActivas.collectAsState()
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            GeneralLbl(texto = "Activar notificaciones", tamano = 18.sp)
            Switch(
                checked = estadoNotificaciones,
                onCheckedChange = { viewModel.cambiarEstadoNotificaciones(it) }
            )
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
        //SeleccionHora()
        SeleccionHoraVisual()
        FormularioActualizar(state,viewModel)
        BotonBox(texto = "Cerrar sesion", background = 0xFFB70000){viewModel.onEvent(ConfiguracionEvent.btnCerrarSesionClicked)}
        Spacer(Modifier.height(35.dp))
    }


}
@Composable
fun FormularioActualizar(state: ConfiguracionState,viewModel: ConfiguracionViewModel){

    Box(
        modifier = Modifier
            .padding(top = 10.dp, bottom = 15.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(10))
            .background(Color(0x5C359B94))

    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp, vertical = 15.dp)
        ) {
            GeneralLbl(
                texto = "Cambia tu contrasena",
                tamano = 20.sp,
                modifier = Modifier.fillMaxWidth(),
                alineacionTexto = TextAlign.Center
            )
            TxtFieldGeneral(
                valor = state.correo,
                etiqueta = "Correo",
                placeholderTexto = "Ejemplo@gmail.com",
            ) {viewModel.onEvent(ConfiguracionEvent.correoChanged(it))}
            TxtFieldGeneral(
                valor = state.contrasena,
                etiqueta = "Contrasena",
                placeholderTexto = "Ejemplo:Ejemplo123!",
            ) {viewModel.onEvent(ConfiguracionEvent.contrasenaChanged(it))}
            TxtFieldGeneral(
                valor = state.confirmarContrasena,
                etiqueta = "Confirma contrasena",
                placeholderTexto = "Ejemplo:Ejemplo123!"
            ) {viewModel.onEvent(ConfiguracionEvent.confirmarContrasenaChanged(it))}
            Spacer(Modifier.height(15.dp))
            BotonBox(
                modifier = Modifier,
                "Guardar",
                background = 0xFF41837B)
            {viewModel.onEvent(ConfiguracionEvent.btnActualizarClicked)}
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


@Composable
fun BotonBox(modifier: Modifier= Modifier, texto: String, background: Long, onClick: () -> Unit) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp)
            .clip(shape = RoundedCornerShape(40))
            .background(Color(background))
            .clickable { onClick() },
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
@Composable
fun HoraCardSelector(
    hora: Int,
    minuto: Int,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(Color(0xFF359B94))
            .clickable { onClick() }
            .padding(20.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Horario seleccionado",
                fontSize = 18.sp,
                color = Color.White
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = String.format("%02d:%02d", hora, minuto),
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = "Toca para cambiar",
                fontSize = 14.sp,
                color = Color.White.copy(alpha = 0.7f)
            )
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HoraDialog(
    onDismiss: () -> Unit,
    onHoraSeleccionada: (Int, Int) -> Unit
) {
    val state = rememberTimePickerState()

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                onHoraSeleccionada(state.hour, state.minute)
                onDismiss()
            }) {
                Text("Aceptar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        },
        title = { Text("Selecciona una hora") },
        text = {
            TimePicker(
                state = state,
                modifier = Modifier.fillMaxWidth(),
                colors = TimePickerDefaults.colors(),
                layoutType = TimePickerDefaults.layoutType()
            )
        }
    )
}
@Composable
fun SeleccionHoraVisual(viewModel: ConfiguracionViewModel = hiltViewModel()) {
    val context = LocalContext.current
    val showDialog = remember { mutableStateOf(false) }
    // Observar el valor guardado desde DataStore
    val horarioGuardado by viewModel.horarioGuardado.collectAsState()

    // Parsear hora y minuto desde el string guardado
    val (hora, minuto) = horarioGuardado.split(":").map { it.toInt() }

    HoraCardSelector(
        hora = hora,
        minuto = minuto,
        onClick = { showDialog.value = true }
    )

    if (showDialog.value) {
        HoraDialog(
            onDismiss = { showDialog.value = false },
            onHoraSeleccionada = { h, m ->
                viewModel.guardarHorarioRespiracion(h, m)
                viewModel.programarRecordatorio(context, h, m)
            }
        )
    }


}







