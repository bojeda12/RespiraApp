package com.example.examplemvvm.data.local.session

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

// Extension para crear el DataStore
private val Context.dataStore by preferencesDataStore(name = "sesion_usuario")

class SesionManager @Inject constructor(@ApplicationContext context: Context) {

    private val appContext = context.applicationContext

    companion object {
        val ID_USUARIO = intPreferencesKey("id_usuario")
        val NOMBRE_USUARIO = stringPreferencesKey("nombre_usuario")
    }

    // Usamos appContext para acceder a dataStore
    suspend fun guardarSesion(id: Int, nombre: String) {
        appContext.dataStore.edit { prefs ->
            prefs[ID_USUARIO] = id
            prefs[NOMBRE_USUARIO] = nombre
        }
    }

    val idUsuario: Flow<Int?> = appContext.dataStore.data
        .map { it[ID_USUARIO] }

    val nombreUsuario: Flow<String> = appContext.dataStore.data
        .map { it[NOMBRE_USUARIO] ?: "" }

    suspend fun cerrarSesion() {
        appContext.dataStore.edit { prefs ->
            prefs.clear()
        }
    }
}




