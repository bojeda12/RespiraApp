package com.example.examplemvvm.domain.repository

import com.example.examplemvvm.domain.model.Usuario


interface UsuarioRepository {
    suspend fun getUsuarios(): List<Usuario>
    suspend fun insertUsuario(usuario: Usuario)
    suspend fun deleteUsuario(usuario: Usuario)
}

