package com.example.reloj.presentation.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import com.example.reloj.presentation.sync.MessageSender

@Composable
fun EstadoAnimoScreen() {
    val emociones = listOf("😄 Muy bien", "😊 Bien", "🙂 Neutro", "🥲 Mal", "😭 Muy mal")
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Elige tu estado de ánimo", fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(10.dp))

        LazyColumn {
            itemsIndexed(emociones) { index, item ->
                Button(
                    onClick = {
                        scope.launch {
                            MessageSender.enviarEstadoAnimo(context, index + 1)
                        }
                    },
                    modifier = Modifier.fillMaxWidth(0.9f),
                    colors = ButtonDefaults.buttonColors(Color(0xFF359B94))
                ) {
                    Text(item)
                }
            }
        }
    }
}