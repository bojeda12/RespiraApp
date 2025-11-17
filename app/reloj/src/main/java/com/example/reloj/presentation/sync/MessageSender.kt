package com.example.reloj.presentation.sync

import android.content.Context
import android.util.Log
import com.google.android.gms.wearable.Wearable
import kotlinx.coroutines.tasks.await

object MessageSender {
    suspend fun enviarEstadoAnimo(context: Context, emocion: Int) {
        val nodes = Wearable.getNodeClient(context).connectedNodes.await()
        Log.d("WearSender", "Nodos conectados: ${nodes.map { it.displayName }}")
        val client = Wearable.getMessageClient(context)

        nodes.forEach { node ->
            client.sendMessage(
                node.id,
                "/estado_animo",
                byteArrayOf(emocion.toByte())
            ).await()
        }
    }
}

