package com.example.examplemvvm.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.examplemvvm.data.local.entities.RegistroEstadoAnimoEntity

@Dao
interface RegistroEstadoAnimoDao {
    @Query("SELECT * FROM registroestadoanimo WHERE id_usuario = :usuarioId")
    suspend fun getRegistrosByUsuario(usuarioId: Int): List<RegistroEstadoAnimoEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarEstado(estado: RegistroEstadoAnimoEntity)
}