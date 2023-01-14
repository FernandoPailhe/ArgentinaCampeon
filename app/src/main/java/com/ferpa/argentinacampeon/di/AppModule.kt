package com.ferpa.argentinacampeon.di

import android.content.Context
import com.ferpa.argentinacampeon.data.local.PhotoDao
import com.ferpa.argentinacampeon.domain.repository.PhotoRepository
import com.ferpa.argentinacampeon.data.repository.PhotoRepositoryImpl
import com.ferpa.argentinacampeon.data.remote.ArgentinaCampeonService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providePhotoRepository(
        photoDao: PhotoDao,
        photoSource: ArgentinaCampeonService,
        @ApplicationContext context: Context
    ): PhotoRepository = PhotoRepositoryImpl(
        photoDao,
        photoSource,
        context
    )

}