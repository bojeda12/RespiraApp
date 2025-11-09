package com.example.examplemvvm.ui.screens.componentes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerColors
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.TimePickerLayoutType
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerGenerico(
    // Hora y minuto inicial
    initialHour: Int = Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
    initialMinute: Int = Calendar.getInstance().get(Calendar.MINUTE),
    // Formato 24h o 12h
    is24Hour: Boolean = true,
    // Callback para enviar la hora seleccionada
    onTimeSelected: (hour: Int, minute: Int) -> Unit,
    // Opciones de UI
    showSelectedTimeText: Boolean = true,
    modifier: Modifier = Modifier,
    timePickerColors: TimePickerColors = TimePickerDefaults.colors(),
    timePickerLayoutType: TimePickerLayoutType = TimePickerDefaults.layoutType()
) {
    val state = rememberTimePickerState(
        initialHour = initialHour,
        initialMinute = initialMinute,
        is24Hour = is24Hour
    )

    // Llamamos al callback cuando cambie la hora o minuto
    LaunchedEffect(state.hour, state.minute) {
        onTimeSelected(state.hour, state.minute)
    }

    // Formateamos la hora en 12h si is24Hour es false
    var formattedHour by remember { mutableStateOf("") }

    LaunchedEffect(state.hour, state.minute, is24Hour) {
        formattedHour = if (!is24Hour) {
            "%02d:%02d".format(state.hour, state.minute)
        } else {
            val amPm = if (state.hour < 12) "a.m." else "p.m."
            val hour12 = when {
                state.hour == 0 -> 12
                state.hour > 12 -> state.hour - 12
                else -> state.hour
            }
            "%02d:%02d %s".format(hour12, state.minute, amPm)
        }
    }


    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        TimePicker(
            state = state,
            colors = timePickerColors,
            layoutType = timePickerLayoutType
        )
        if (showSelectedTimeText) {
            Spacer(Modifier.height(8.dp))
            Text(text = "Hora seleccionada: $formattedHour")
        }
    }
}
