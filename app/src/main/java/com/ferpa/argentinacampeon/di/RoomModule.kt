package com.ferpa.argentinacampeon.di

import android.content.Context
import androidx.room.Room
import com.ferpa.argentinacampeon.data.PhotoDao
import com.ferpa.argentinacampeon.data.PhotoRoomDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    @Singleton
    fun providePhotoRoomDatabase(@ApplicationContext context: Context): PhotoRoomDatabase {
        return Room.databaseBuilder(
            context,
            PhotoRoomDatabase::class.java,
            PhotoRoomDatabase.DATABASE_NAME
        )
            .build()
    }

    @Provides
    @Singleton
    fun providePhotoDao(photoRoomDatabase: PhotoRoomDatabase): PhotoDao {
        return photoRoomDatabase.photoDao()
    }
}
