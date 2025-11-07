package com.example.examplemvvm.di

import android.content.Context
import com.example.examplemvvm.data.local.session.SesionManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SesionModule {

    @Provides
    @Singleton
    fun provideSesionManager(@ApplicationContext context: Context): SesionManager {
        return SesionManager(context)
    }
}

