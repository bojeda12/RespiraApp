package com.example.examplemvvm.data.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "recomendacion",
    foreignKeys = [
        ForeignKey(
            entity = UsuarioEntity::class,
            parentColumns = ["id"],
            childColumns = ["id_usuario"],
            onDelete = CASCADE
        )
    ],
    indices = [Index("id_usuario")]
)
data class RecomendacionEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val tipoRecomendacion: String,
    val id_usuario: Int
)


