package com.example.examplemvvm.data.local.database


import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.examplemvvm.data.local.entities.*
import com.example.examplemvvm.data.local.dao.*

@Database(
    entities = [
        UsuarioEntity::class,
        RegistroEstadoAnimoEntity::class,
        SesionRespiracionEntity::class,
        TipoRespiracionEntity::class,
        RecomendacionEntity::class
    ],
    version = 4,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun usuarioDao(): UsuarioDao
    abstract fun registroEstadoAnimoDao(): RegistroEstadoAnimoDao
    abstract fun sesionRespiracionDao(): SesionRespiracionDao
    abstract fun tipoRespiracionDao(): TipoRespiracionDao
    abstract fun recomendacionDao(): RecomendacionDao
}