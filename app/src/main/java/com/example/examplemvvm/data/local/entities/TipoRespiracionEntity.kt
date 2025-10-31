package com.example.examplemvvm.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tiporespiracion")
data class TipoRespiracionEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val Nom_Respiracion: String
)



