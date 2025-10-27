package com.example.examplemvvm.di


import android.content.Context
import androidx.room.Room
import com.example.examplemvvm.data.local.dao.UsuarioDao
import com.example.examplemvvm.data.local.database.AppDatabase
import com.example.examplemvvm.data.repository.UsuarioRepositoryImpl
import com.example.examplemvvm.domain.repository.UsuarioRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(context: Context): AppDatabase =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "app_database"
        ).fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideUsuarioDao(db: AppDatabase): UsuarioDao = db.usuarioDao()

    @Provides
    fun provideUsuarioRepository(dao: UsuarioDao): UsuarioRepository =
        UsuarioRepositoryImpl(dao)
}