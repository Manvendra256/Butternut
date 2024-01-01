package com.example.butternut.di

import android.content.Context
import androidx.room.Room
import com.example.butternut.data.ButternutDatabase
import com.example.butternut.data.ClassDao
import com.example.butternut.data.DeckDao
import com.example.butternut.data.FlashCardDao
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): ButternutDatabase {
        return Room.databaseBuilder(context, ButternutDatabase::class.java, "item_database")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideClassDao(database: ButternutDatabase): ClassDao {
        return database.classDao()
    }

    @Provides
    fun provideDeckDao(database: ButternutDatabase): DeckDao {
        return database.deckDao()
    }

    @Provides
    fun provideFlashCardDao(database: ButternutDatabase): FlashCardDao {
        return database.flashCardDao()
    }
}