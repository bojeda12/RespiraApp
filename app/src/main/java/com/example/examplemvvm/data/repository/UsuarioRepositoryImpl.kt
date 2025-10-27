package com.example.examplemvvm.data.repository

import com.example.examplemvvm.data.local.dao.UsuarioDao
import com.example.examplemvvm.data.local.mapper.toEntity
import com.example.examplemvvm.data.local.mapper.toModel
import com.example.examplemvvm.domain.model.Usuario
import com.example.examplemvvm.domain.repository.UsuarioRepository

class UsuarioRepositoryImpl(
    private val dao: UsuarioDao
) : UsuarioRepository {

    override suspend fun getUsuarios(): List<Usuario> {
        return dao.getAllUsuarios().map { it.toModel() }
    }

    override suspend fun insertUsuario(usuario: Usuario) {
        dao.insertUsuario(usuario.toEntity())
    }

    override suspend fun deleteUsuario(usuario: Usuario) {
        dao.deleteUsuario(usuario.toEntity())
    }
}
