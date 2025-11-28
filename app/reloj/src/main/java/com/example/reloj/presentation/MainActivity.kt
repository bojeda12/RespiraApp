package com.example.reloj.presentation.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.reloj.presentation.sync.SugerenciasReceiver
import com.google.android.gms.wearable.Wearable

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WearApp()
        }
    }

    override fun onResume() {
        super.onResume()
        Log.d("MainActivity", "onResume → registrando listener")
        Wearable.getMessageClient(this).addListener(SugerenciasReceiver)
    }

    override fun onPause() {
        super.onPause()
        Log.d("MainActivity", "onPause → quitando listener")
        Wearable.getMessageClient(this).removeListener(SugerenciasReceiver)
    }
}