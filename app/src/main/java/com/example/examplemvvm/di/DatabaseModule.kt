package com.example.examplemvvm.di


import android.content.Context
import androidx.room.Room
import com.example.examplemvvm.data.local.dao.RecomendacionDao
import com.example.examplemvvm.data.local.dao.RegistroEstadoAnimoDao
import com.example.examplemvvm.data.local.dao.SesionRespiracionDao
import com.example.examplemvvm.data.local.dao.TipoRespiracionDao
import com.example.examplemvvm.data.local.dao.UsuarioDao
import com.example.examplemvvm.data.local.database.AppDatabase
import com.example.examplemvvm.data.local.session.SesionManager
import com.example.examplemvvm.data.repository.EstadoAnimoRepositoryImpl
import com.example.examplemvvm.data.repository.UsuarioRepositoryImpl
import com.example.examplemvvm.domain.repository.EstadoAnimoRepository
import com.example.examplemvvm.domain.repository.UsuarioRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "app_database"
        ).fallbackToDestructiveMigration()//Borrar despues porqeue esto borra la base de datos si existe si no hemos definido una migracion
            .build()

    @Provides
    fun provideUsuarioDao(db: AppDatabase): UsuarioDao = db.usuarioDao()
    @Provides
    fun provideRegistroEstadoAnimoDao(db: AppDatabase): RegistroEstadoAnimoDao = db.registroEstadoAnimoDao()
    @Provides
    fun provideSesionRespiracionDao(db: AppDatabase): SesionRespiracionDao = db.sesionRespiracionDao()
    @Provides
    fun provideTipoRespiracionDao(db: AppDatabase): TipoRespiracionDao = db.tipoRespiracionDao()
    @Provides
    fun provideRecomendacionDao(db: AppDatabase): RecomendacionDao = db.recomendacionDao()

    @Provides
    fun provideUsuarioRepository(
        usuarioDao: UsuarioDao,
        registroEstadoAnimoDao: RegistroEstadoAnimoDao,
        sesionsDao: SesionRespiracionDao
    ): UsuarioRepository{
        return UsuarioRepositoryImpl(usuarioDao,registroEstadoAnimoDao,sesionsDao)
    }
    @Provides
    fun provideEstadoAnimoRepository(
        registroEstadoAnimoDao: RegistroEstadoAnimoDao,
        sesionManager: SesionManager
    ): EstadoAnimoRepository {
        return EstadoAnimoRepositoryImpl(registroEstadoAnimoDao,sesionManager)
    }



}