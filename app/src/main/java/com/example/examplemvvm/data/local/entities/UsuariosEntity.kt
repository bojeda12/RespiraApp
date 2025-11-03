package com.example.examplemvvm.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "usuario")
data class UsuarioEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val nombre_usuario: String,
    val correo: String,
    val contrasena: String
)


