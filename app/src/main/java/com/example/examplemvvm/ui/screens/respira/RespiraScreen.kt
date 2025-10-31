package com.example.examplemvvm.ui.screens.respira

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.example.examplemvvm.ui.screens.componentes.Container
import com.example.examplemvvm.ui.screens.rutinas.RutinaViewModel
import kotlinx.coroutines.delay


@Composable
fun RespiraScreen(viewModel: RespiraViewModel = RespiraViewModel(), goToDashboard: () -> Unit) {
    LaunchedEffect(Unit) {
        viewModel.navigationEvent.collect { event ->
            when (event) {
                is RespiraViewModel.NavigationTarget.goToDashboard -> goToDashboard()
            }
        }
    }
    Container(
        showEncabezado = true,
        showHomeButton = true,
        onHomeClick = {viewModel.onEvent(RespiraEvent.btnDashboardClicked)},
        encabezado = "Hora de respirar"
    ) {
        RespireAnimation()
    }

}

@Composable
fun LblEncabezado() {

}

enum class BreathingState {
    Inhaling,
    Exhaling
}

@Composable
fun RespireAnimation() {
    val colors = listOf(
        Color(0xFFa8dadc), // turquesa suave
        Color(0xFF457b9d), // azul verdoso profundo
        Color(0xFF2a9d8f), // verde turquesa
        Color(0xFF52b788), // verde menta
        Color(0xFF74c69d), // verde claro
        Color(0xFF95d5b2), // verde pastel
        Color(0xFFb7e4c7), // verde agua
        Color(0xFFcaffbf), // verde muy claro
        Color(0xFF80ed99), // verde vibrante
        Color(0xFF38b000), // verde intenso
        Color(0xFFa8dadc)  // repetido para cerrar el gradiente
    )

    var breathingState by remember { mutableStateOf(BreathingState.Inhaling) }

    val transition = updateTransition(targetState = breathingState, label = "breathing_transition")

    // Duraciones
    val inhaleDuration = 4000
    val exhaleDuration = 4000
    val holdDuration = 2000

    val animatedSize by transition.animateDp(
        transitionSpec = {
            if (targetState == BreathingState.Inhaling)
                tween(inhaleDuration, easing = LinearOutSlowInEasing)
            else
                tween(exhaleDuration, easing = FastOutSlowInEasing)
        },
        label = "size_animation"
    ) { state ->
        if (state == BreathingState.Inhaling) 240.dp else 180.dp
    }

    val textAlpha by transition.animateFloat(
        transitionSpec = { tween(1000) },
        label = "text_alpha"
    ) { state ->
        when (state) {
            BreathingState.Inhaling -> 1f
            BreathingState.Exhaling -> 0.7f
        }
    }

    val animatedSpread by transition.animateFloat(
        transitionSpec = {
            if (targetState == BreathingState.Inhaling)
                tween(inhaleDuration, easing = FastOutSlowInEasing)
            else
                tween(exhaleDuration, easing = FastOutSlowInEasing)
        },
        label = "spread_animation"
    ) { state ->
        if (state == BreathingState.Inhaling) 10f else 2f
    }

    // ✅ Aquí ya no usamos animateFloat, solo valor fijo
    val animatedAlpha = 1f

    val breathingText = when (breathingState) {
        BreathingState.Inhaling -> "Inhala"
        BreathingState.Exhaling -> "Exhala"
    }

    // Ciclo de respiración
    LaunchedEffect(breathingState) {
        val duration =
            if (breathingState == BreathingState.Inhaling) inhaleDuration else exhaleDuration
        delay(duration.toLong() + holdDuration)
        breathingState = if (breathingState == BreathingState.Inhaling)
            BreathingState.Exhaling else BreathingState.Inhaling
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(animatedSize)
                .dropShadow(
                    shape = RoundedCornerShape(70.dp),
                    shadow = Shadow(
                        radius = 10.dp,
                        spread = animatedSpread.dp,
                        brush = Brush.sweepGradient(colors),
                        offset = DpOffset(0.dp, 0.dp),
                        alpha = animatedAlpha // valor fijo aquí
                    )
                )
                .clip(RoundedCornerShape(70.dp))
                .background(Color(0xEDFFFFFF)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = breathingText,
                modifier = Modifier.alpha(textAlpha),
                color = Color.Black,
                style = MaterialTheme.typography.headlineMedium
            )
        }
    }
}

