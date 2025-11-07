package com.example.examplemvvm.data.local.session

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

// Extension para crear el DataStore
private val Context.dataStore by preferencesDataStore(name = "sesion_usuario")

class SesionManager @Inject constructor(@ApplicationContext private val context: Context) {

    companion object {
        val KEY_ID_USUARIO = intPreferencesKey("id_usuario")
    }

    val idUsuario: Flow<Int?> = context.dataStore.data.map { prefs ->
        prefs[KEY_ID_USUARIO]
    }

    suspend fun guardarSesion(idUsuario: Int) {
        context.dataStore.edit { prefs ->
            prefs[KEY_ID_USUARIO] = idUsuario
        }
    }

    suspend fun cerrarSesion() {
        context.dataStore.edit { prefs ->
            prefs.remove(KEY_ID_USUARIO)
        }
    }
}

