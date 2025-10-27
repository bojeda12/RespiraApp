package com.example.examplemvvm.data.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(
    tableName = "registroestadoanimo",
    foreignKeys = [ForeignKey(
        entity = UsuarioEntity::class,
        parentColumns = ["id"],
        childColumns = ["id_usuario"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index(value = ["id_usuario"])]
)
data class RegistroEstadoAnimoEntity(
    @PrimaryKey val id: Int,
    val fecha: String,
    val estadoAnimo: String,
    val id_usuario: Int
)


