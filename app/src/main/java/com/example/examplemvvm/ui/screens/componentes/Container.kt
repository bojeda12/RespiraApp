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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp


@Composable
fun Container(
    modifier: Modifier = Modifier,
    topPadding: Dp = 100.dp,
    horizontalPadding: Dp = 35.dp,
    cornerRadius: Dp = 30.dp,
    backgroundColor: Color = MaterialTheme.colorScheme.background,
    showBackButton: Boolean = false,
    showHomeButton: Boolean = false,
    showConfiguracion:Boolean = false,
    showEncabezado: Boolean = false,
    encabezado:String = "",
    onBackClick: () -> Unit = {},
    onHomeClick: () -> Unit = {},
    content: @Composable ColumnScope.() -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1A78C5))
        //.padding(top = topPadding)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 55.dp, start = 25.dp, end = 25.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            if (showBackButton) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clickable(
                            indication = null, // 🔥 elimina el efecto ripple
                            interactionSource = remember { MutableInteractionSource() }
                        ) {
                            onBackClick()
                        }
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.back),
                        contentDescription = "Botón de acción",
                        modifier = Modifier.size(34.dp)
                    )
                }
            } else {
                Spacer(modifier = Modifier.size(48.dp))
            }
            if (showEncabezado){
                Text(
                    text = encabezado,
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 7.dp, start = 90.dp, end = 30.dp)
                )
            }

            if (showHomeButton) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clickable(
                            indication = null, // 🔥 elimina el efecto ripple
                            interactionSource = remember { MutableInteractionSource() }
                        ) {
                            onHomeClick()
                        }
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.homeb),
                        contentDescription = "Botón de acción",
                        modifier = Modifier.size(34.dp)
                    )
                }
            } else {
                Spacer(modifier = Modifier.size(48.dp))
            }
            if(showConfiguracion){
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clickable(
                            indication = null, // 🔥 elimina el efecto ripple
                            interactionSource = remember { MutableInteractionSource() }
                        ) {
                            onHomeClick()
                        }
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.config),
                        contentDescription = "Botón de acción",
                        modifier = Modifier.size(34.dp)
                    )
                }
            }else {
                Spacer(modifier = Modifier.size(48.dp))
            }
        }

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

