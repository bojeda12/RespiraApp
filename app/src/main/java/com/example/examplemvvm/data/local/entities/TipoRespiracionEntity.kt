package com.example.examplemvvm.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tiporespiracion")
data class TipoRespiracionEntity(
    @PrimaryKey val id: Int,
    val Nom_Respiracion: String
)



