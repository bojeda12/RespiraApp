package com.example.examplemvvm.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recomendacion")
data class RecomendacionEntity(
    @PrimaryKey val id: Int,
    val tipoRecomendacion: String
)
