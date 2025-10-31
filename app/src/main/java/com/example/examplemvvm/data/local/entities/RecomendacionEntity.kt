package com.example.examplemvvm.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recomendacion")
data class RecomendacionEntity(
    @PrimaryKey (autoGenerate = true) val id: Int = 0,
    val tipoRecomendacion: String
)
