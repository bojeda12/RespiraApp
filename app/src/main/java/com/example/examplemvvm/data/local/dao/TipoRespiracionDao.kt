package com.example.examplemvvm.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.examplemvvm.data.local.entities.TipoRespiracionEntity

@Dao
interface TipoRespiracionDao {
    @Query("SELECT * FROM tiporespiracion")
    suspend fun getAllTipos(): List<TipoRespiracionEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTipo(tipo: TipoRespiracionEntity)

}