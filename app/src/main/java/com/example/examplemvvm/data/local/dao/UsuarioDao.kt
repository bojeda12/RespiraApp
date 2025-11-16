package com.example.examplemvvm.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.examplemvvm.data.local.entities.UsuarioEntity

@Dao
interface UsuarioDao {
    @Query("SELECT * FROM usuario")
    suspend fun getAllUsuarios(): List<UsuarioEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarUsuario(usuario: UsuarioEntity): Long

    @Delete
    suspend fun deleteUsuario(usuario: UsuarioEntity)

    @Query("SELECT COUNT(*) FROM usuario WHERE correo = :correo")
    suspend fun contarPorCorreo(correo: String): Int
    @Query("UPDATE usuario SET contrasena = :nueva WHERE correo = :correo")
    suspend fun actualizarContrasena(correo: String, nueva: String): Int

    @Query("SELECT * FROM usuario WHERE correo = :correo LIMIT 1")
    suspend fun obtenerUsuarioPorCorreo(correo: String): UsuarioEntity?


}