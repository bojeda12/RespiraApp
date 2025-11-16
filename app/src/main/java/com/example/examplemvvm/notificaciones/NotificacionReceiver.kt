package com.example.examplemvvm.notificaciones


import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.examplemvvm.MainActivity
import com.example.examplemvvm.R

class NotificacionReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        Log.d("NotificacionReceiver1", "¡Receiver activado!")

        val canalId = "canal_respiracion"
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val canal = NotificationChannel(
                canalId,
                "Recordatorios de respiración",
                NotificationManager.IMPORTANCE_HIGH
            )
            manager.createNotificationChannel(canal)
        }
        val intent = Intent(context, MainActivity::class.java).apply {
            putExtra("destino", "respira")
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )


        val notification = NotificationCompat.Builder(context, canalId)
            .setSmallIcon(R.drawable.respira)
            .setContentTitle("Hora de respirar")
            .setContentText("Tómate un momento para hacer tu rutina de respiración.")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .build()

        manager.notify(1001, notification)
    }
}