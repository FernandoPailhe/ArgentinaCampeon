package com.ferpa.argentinacampeon.di

import com.ferpa.argentinacampeon.common.ServerRoutes
import com.ferpa.argentinacampeon.data.remote.ArgentinaCampeonService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Provides
    @Singleton
    fun provideArgentinaCampeonService(): ArgentinaCampeonService {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(ServerRoutes.BASE_URL)
            .build()
            .create(ArgentinaCampeonService::class.java)
    }
}