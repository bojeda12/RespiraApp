package com.example.examplemvvm.presentation.sync

import android.content.Context
import android.util.Log
import com.example.examplemvvm.data.remote.response.HorarioItem
import com.google.android.gms.tasks.Tasks
import com.google.android.gms.wearable.Wearable
import com.google.gson.Gson

object MessageSender {
    fun enviarHorarios(context: Context, horarios: List<HorarioItem>) {
        val client = Wearable.getMessageClient(context)
        val nodeClient = Wearable.getNodeClient(context)

        // Obtener el nodo del reloj conectado
        Thread {
            try {
                val nodes = Tasks.await(nodeClient.connectedNodes)
                val nodeId = nodes.firstOrNull()?.id

                if (nodeId != null) {
                    val json = Gson().toJson(horarios)
                    Log.d("PhoneSender", "JSON enviado: $json")
                    val path = "/wear/horarios"
                    val data = json.toByteArray()

                    client.sendMessage(nodeId, path, data)
                } else {
                    Log.e("MessageSender", "No se encontró nodo Wear OS conectado")
                }
            } catch (e: Exception) {
                Log.e("MessageSender", "Error al enviar mensaje: ${e.message}")
            }
        }.start()
    }
}
