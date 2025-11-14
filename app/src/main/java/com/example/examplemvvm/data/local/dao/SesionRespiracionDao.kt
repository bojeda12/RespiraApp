package com.example.examplemvvm.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.examplemvvm.data.local.entities.SesionRespiracionEntity

@Dao
interface SesionRespiracionDao {
    @Query("SELECT * FROM sesionesrespiracion WHERE id_usuario = :usuarioId")
    suspend fun obtenerSesionesUsuario(usuarioId: Int): List<SesionRespiracionEntity>

    @Query("SELECT * FROM sesionesrespiracion WHERE id_usuario = :idUsuario ORDER BY id DESC LIMIT 1")
    suspend fun obtenerUltimaSesion(idUsuario: Int): SesionRespiracionEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun guardarSesion(sesion: SesionRespiracionEntity)
}

