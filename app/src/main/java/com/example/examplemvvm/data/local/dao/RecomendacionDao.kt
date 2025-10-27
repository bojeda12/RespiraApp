package com.example.examplemvvm.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.examplemvvm.data.local.entities.RecomendacionEntity

@Dao
interface RecomendacionDao {
    @Query("SELECT * FROM recomendacion")
    suspend fun getAllRecomendaciones(): List<RecomendacionEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecomendacion(recomendacion: RecomendacionEntity)
}

