package com.example.examplemvvm.ui.screens.componentes

import android.graphics.drawable.Icon
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.examplemvvm.R
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp


@Composable
fun Container(
    modifier: Modifier = Modifier,
    topPadding: Dp =70.dp,
    horizontalPadding: Dp = 35.dp,
    cornerRadius: Dp = 30.dp,
    backgroundColor: Color = MaterialTheme.colorScheme.background,
    showBackButton: Boolean = false,
    showHomeButton: Boolean = false,
    showConfiguracion: Boolean = false,
    showEncabezado: Boolean = false,
    encabezado: String = "",
    onBackClick: () -> Unit = {},
    onHomeClick: () -> Unit = {},

    content: @Composable ColumnScope.() -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1A78C5))
            .windowInsetsPadding(WindowInsets.statusBars) // ✅ padding dinámico para status bar
    ) {
        // 🔹 Encabezado con botones alineados
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            if (showBackButton) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(RoundedCornerShape(50))
                        .clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }
                        ) { onBackClick() },
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.back),
                        contentDescription = "Atrás",
                        modifier = Modifier.size(48.dp)
                    )
                }
            } else {
                Spacer(modifier = Modifier.size(48.dp))
            }

            if (showEncabezado) {
                Text(
                    text = encabezado,
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f)
                )
            } else {
                Spacer(modifier = Modifier.weight(1f))
            }

            if (showHomeButton || showConfiguracion) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(RoundedCornerShape(50))
                        .clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }
                        ) { onHomeClick() },
                    contentAlignment = Alignment.Center
                ) {
                    val iconRes = if (showHomeButton) R.drawable.home else R.drawable.setting
                    Image(
                        painter = painterResource(id = iconRes),
                        contentDescription = "Botón derecho",
                        modifier = Modifier.size(40.dp)
                    )
                }
            } else {
                Spacer(modifier = Modifier.size(48.dp))
            }
        }

        //Contenido principal de la aplicacion
        Box(
            modifier = Modifier
                .padding(top = topPadding)
                .fillMaxSize()
                .clip(RoundedCornerShape(topStart = cornerRadius, topEnd = cornerRadius))
                .background(backgroundColor)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = horizontalPadding),
                content = content
            )
        }
    }
}



