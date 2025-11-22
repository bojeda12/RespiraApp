package com.example.examplemvvm.di

import com.example.examplemvvm.data.local.dao.RegistroEstadoAnimoDao
import com.example.examplemvvm.data.local.dao.SesionRespiracionDao
import com.example.examplemvvm.data.repository.MLRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideMLRepository(
        sesionDao: SesionRespiracionDao,
        estadoDao: RegistroEstadoAnimoDao
    ): MLRepository {
        return MLRepository(sesionDao, estadoDao)
    }
}

