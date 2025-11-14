package com.example.examplemvvm.ui.screens.respira

import android.widget.Space
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.room.util.TableInfo
import com.example.examplemvvm.ui.screens.componentes.Container
import kotlinx.coroutines.delay


@Composable
fun RespiraScreen(
    viewModel: RespiraViewModel = hiltViewModel(),
    goToDashboard: () -> Unit
) {
    val activo by viewModel.cronometroActivo.collectAsState()
    val tiempoMillis by viewModel.tiempoTranscurrido.collectAsState()
    val context = LocalContext.current

    val segundosTotales = (tiempoMillis / 1000L).toInt()
    val minutos = segundosTotales / 60
    val segundos = segundosTotales % 60
    val tiempoDisplay = String.format("%02d:%02d", minutos, segundos)



    LaunchedEffect(Unit) {
        viewModel.navigationEvent.collect { event ->
            when (event) {
                is RespiraViewModel.NavigationTarget.GoToDashboard -> goToDashboard()
            }
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFFa8dadc), Color(0xFF457b9d))
                )
            )
            .padding(24.dp)
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Hora de respirar",
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White
            )

            Spacer(Modifier.height(32.dp))


            RespireAnimation(activo = activo)


            Spacer(Modifier.height(32.dp))

            Text(
                text = "Duración: $tiempoDisplay",
                style = MaterialTheme.typography.titleLarge,
                color = Color.White
            )

            Spacer(Modifier.height(24.dp))

            if (!activo) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        onClick = { viewModel.onEvent(RespiraEvent.BtnStartClicked) },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF52b788))
                    ) {
                        Text("Iniciar", color = Color.White)
                    }
                    Spacer(Modifier.width(10.dp))
                    Button(
                        onClick = { viewModel.onEvent(RespiraEvent.btnDashboardClicked) },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2196F3))
                    ) {
                        Text("Salir", color = Color.White)
                    }

                }

            } else {
                Button(
                    onClick = { viewModel.onEvent(RespiraEvent.BtnStopClicked) },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFe76f51))
                ) {
                    Text("Detener", color = Color.White)
                }
            }
        }


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
fun RespireAnimation(activo: Boolean) {
    if (!activo) return

    var breathingState by remember { mutableStateOf(BreathingState.Inhaling) }

    val transition = updateTransition(targetState = breathingState, label = "breathing_transition")

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

    val breathingText = when (breathingState) {
        BreathingState.Inhaling -> "Inhala"
        BreathingState.Exhaling -> "Exhala"
    }

    LaunchedEffect(breathingState) {
        val duration =
            if (breathingState == BreathingState.Inhaling) inhaleDuration else exhaleDuration
        delay(duration.toLong() + holdDuration)
        breathingState = if (breathingState == BreathingState.Inhaling)
            BreathingState.Exhaling else BreathingState.Inhaling
    }
    val borderWidth = (animatedSize.value * 0.02f).dp // 1% del tamaño

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(animatedSize)
                .clip(CircleShape)
                .background(
                    Brush.radialGradient(
                        colors = listOf(
                            Color.White.copy(alpha = 0.3f),
                            Color.White.copy(alpha = 0.05f)
                        ),
                        radius = animatedSize.value * 0.6f
                    )
                )
                .border(
                    width = borderWidth,
                    color = Color.White.copy(alpha = 0.4f),
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = breathingText,
                modifier = Modifier.alpha(textAlpha),
                color = Color.White,
                style = MaterialTheme.typography.headlineMedium
            )
        }
    }
}

