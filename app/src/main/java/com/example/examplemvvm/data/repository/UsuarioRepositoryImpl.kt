package com.example.examplemvvm.data.repository

import android.util.Log
import com.example.examplemvvm.data.local.dao.RegistroEstadoAnimoDao
import com.example.examplemvvm.data.local.dao.SesionRespiracionDao
import com.example.examplemvvm.data.local.dao.UsuarioDao
import com.example.examplemvvm.data.local.mapper.toEntity
import com.example.examplemvvm.data.local.mapper.toModel
import com.example.examplemvvm.domain.model.RegistroEstadoAnimo
import com.example.examplemvvm.domain.model.SesionRespiracion
import com.example.examplemvvm.domain.model.Usuario
import com.example.examplemvvm.domain.repository.UsuarioRepository
import javax.inject.Inject

class UsuarioRepositoryImpl @Inject constructor(
    private val usuarioDao: UsuarioDao,
    private val estadoAnimoDao: RegistroEstadoAnimoDao,
    private val sesionRespiracion: SesionRespiracionDao
) : UsuarioRepository {
    override suspend fun registrarUsuarioCompleto(
        usuario: Usuario,
        estado: RegistroEstadoAnimo,
        sesion: SesionRespiracion
    ){
        val usuarioEntity = usuario.toEntity()
        val idUsuario = usuarioDao.insertarUsuario(usuarioEntity).toInt()
        Log.d("Registro", "ID generado por Room: $idUsuario")
        Log.d("Registro", "Insertando estado con id_usuario = ${idUsuario}")
        Log.d("Registro", "Insertando sesión con id_usuario = ${idUsuario}")

        estadoAnimoDao.insertarEstado(estado.toEntity(idUsuario))
        sesionRespiracion.guardarSesion(sesion.toEntity(idUsuario))
    }
    override suspend fun getUsuarios(): List<Usuario> {
        return usuarioDao.getAllUsuarios().map { it.toModel() }
    }
    override suspend fun existeUsuarioPorCorreo(correo: String): Boolean {
        return usuarioDao.contarPorCorreo(correo) > 0
    }
}
