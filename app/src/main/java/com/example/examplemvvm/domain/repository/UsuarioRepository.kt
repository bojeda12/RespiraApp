package com.example.examplemvvm.domain.repository

import com.example.examplemvvm.domain.model.RegistroEstadoAnimo
import com.example.examplemvvm.domain.model.SesionRespiracion
import com.example.examplemvvm.domain.model.Usuario


interface UsuarioRepository {
    //suspend fun getUsuarios(): List<Usuario>
    //suspend fun insertUsuario(usuario: Usuario)
    //suspend fun deleteUsuario(usuario: Usuario)
    suspend fun registrarUsuarioCompleto(
        usuario: Usuario,
        estado : RegistroEstadoAnimo,
        sesion: SesionRespiracion
    )
    suspend fun getUsuarios(): List<Usuario>
    suspend fun existeUsuarioPorCorreo(correo: String): Boolean
}

