package com.example.examplemvvm.data.local.mapper

import com.example.examplemvvm.data.local.entities.UsuarioEntity
import com.example.examplemvvm.domain.model.Usuario


fun UsuarioEntity.toModel(): Usuario =
    Usuario(id, nombre_usuario, correo, contrasena)

fun Usuario.toEntity(): UsuarioEntity =
    UsuarioEntity(id, nombre_usuario, correo, contrasena)