package com.example.examplemvvm.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.examplemvvm.data.local.entities.SesionRespiracionEntity

@Dao
interface SesionRespiracionDao {
    @Query("SELECT * FROM sesionesrespiracion WHERE id_usuario = :usuarioId")
    suspend fun getSesionesByUsuario(usuarioId: Int): List<SesionRespiracionEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarSesion(sesion: SesionRespiracionEntity)
}

