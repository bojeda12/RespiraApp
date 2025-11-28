package com.example.reloj.presentation.sync


import HorarioItem
import android.util.Log
import com.google.android.gms.wearable.MessageClient
import com.google.android.gms.wearable.MessageEvent
import com.google.gson.Gson
import kotlinx.coroutines.flow.MutableStateFlow

object SugerenciasReceiver : MessageClient.OnMessageReceivedListener {

    val horariosRecibidos = MutableStateFlow<List<HorarioItem>>(emptyList())

    override fun onMessageReceived(event: MessageEvent) {
        Log.d("WearReceiver", "Mensaje recibido en path: ${event.path}")

        if (event.path == "/wear/horarios") {
            try {
                val json = String(event.data)
                Log.d("WearReceiver", "JSON recibido: $json")

                val lista = Gson().fromJson(json, Array<HorarioItem>::class.java).toList()
                horariosRecibidos.value = lista

                Log.d("WearReceiver", "Recomendaciones recibidas: ${lista.size}")
            } catch (e: Exception) {
                Log.e("WearReceiver", "Error al procesar mensaje: ${e.message}")
            }
        }
    }
}