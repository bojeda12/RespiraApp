package com.example.examplemvvm.domain.model

import androidx.room.ColumnInfo

data class Usuario(
    val id: Int,
    val nombre_usuario: String,
    val correo: String,
    val contrasena: String
)