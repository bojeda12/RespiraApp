package com.example.examplemvvm.sync

import android.util.Log
import com.google.android.gms.wearable.*
import com.example.examplemvvm.domain.repository.EstadoAnimoRepository
import com.example.examplemvvm.domain.model.RegistroEstadoAnimo
import com.example.examplemvvm.data.local.session.SesionManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class MessagesReceiver : WearableListenerService(), MessageClient.OnMessageReceivedListener {

    @Inject
    lateinit var estadoAnimoRepository: EstadoAnimoRepository

    @Inject
    lateinit var sesionManager: SesionManager

    override fun onMessageReceived(messageEvent: MessageEvent) {
        if (messageEvent.path == "/estado_animo") {
            val emocion = messageEvent.data.first().toInt()
            val emocionInvertida = 6 - emocion // Ajuste según tu lógica de BD
            Log.d("WearReceiver", "Recibido estado: $emocion")

            CoroutineScope(Dispatchers.IO).launch {
                val usuarioId = sesionManager.idUsuario.firstOrNull()
                if (usuarioId != null && usuarioId > 0) {
                    val fecha = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())
                    val registro = RegistroEstadoAnimo(
                        id = 0,
                        fecha = fecha,
                        estadoAnimo = emocionInvertida.toString(),
                        id_usuario = usuarioId
                    )
                    estadoAnimoRepository.registrarEstado(registro)
                } else {
                    Log.d("WearReceiver", "No hay sesión activa, no se guarda.")
                }
            }
        }
    }

    override fun onCreate() {
        super.onCreate()
        Log.d("WearReceiver", "🟢 Servicio MessagesReceiver iniciado")
        Wearable.getMessageClient(this).addListener(this)
    }

    override fun onDestroy() {
        Wearable.getMessageClient(this).removeListener(this)
        super.onDestroy()
    }
}