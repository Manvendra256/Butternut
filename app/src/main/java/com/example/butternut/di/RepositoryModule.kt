package com.example.butternut.di

import com.example.butternut.data.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier


@Qualifier
annotation class OfflineRepo

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    @OfflineRepo
    fun bindClassesRepository(classesRepository: OfflineClassesRepository) : ClassesRepository


    @Binds
    @OfflineRepo
    fun bindDecksRepository(decksRepository: OfflineDecksRepository): DecksRepository

    @Binds
    @OfflineRepo
    fun bindFlashCardsRepository(flashCardsRepository: OfflineFlashCardsRepository): FlashCardsRepository
}