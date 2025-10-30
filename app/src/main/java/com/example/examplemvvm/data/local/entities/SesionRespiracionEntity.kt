package com.example.examplemvvm.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey
import androidx.room.Index
import java.time.LocalDate

@Entity(
    tableName = "sesionesrespiracion",
    foreignKeys = [
        ForeignKey(entity = UsuarioEntity::class, parentColumns = ["id"], childColumns = ["id_usuario"]),
        ForeignKey(entity = TipoRespiracionEntity::class, parentColumns = ["id"], childColumns = ["id_tiporespiracion"]),
        ForeignKey(entity = RecomendacionEntity::class, parentColumns = ["id"], childColumns = ["id_recomendacion"])
    ],
    indices = [
        Index(value = ["id_usuario"]),
        Index(value = ["id_tiporespiracion"]),
        Index(value = ["id_recomendacion"])
    ]
)
data class SesionRespiracionEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val fecha: String,
    val duracion: Int,
    val horaInicio: String,
    val id_usuario: Int,
    val id_tiporespiracion: Int,
    val id_recomendacion: Int
)
