package com.example.examplemvvm.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.ForeignKey.Companion.SET_NULL
import androidx.room.Index
import java.time.LocalDate

@Entity(
    tableName = "sesionesrespiracion",
    foreignKeys = [
        ForeignKey(entity = UsuarioEntity::class, parentColumns = ["id"], childColumns = ["id_usuario"], onDelete = CASCADE),
        ForeignKey(entity = TipoRespiracionEntity::class, parentColumns = ["id"], childColumns = ["id_tiporespiracion"], onDelete = SET_NULL)
    ],
    indices = [
        Index(value = ["id_usuario"]),
        Index(value = ["id_tiporespiracion"])
    ]
)
data class SesionRespiracionEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val fecha: String,
    val duracion: Int,
    val horaInicio: String,
    val id_usuario: Int,
    val id_tiporespiracion: Int? = null
)
