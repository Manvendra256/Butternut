package com.example.butternut.data

import android.database.Cursor
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

interface FlashCardsRepository {

    fun getByFlashCardId(flashCardId: Long) : Flow<FlashCard>

    fun getFlashCardsByDeckId(deckId: Long): Flow<List<FlashCard>>

    suspend fun getAllBookmarked(): List<FlashCard>

    suspend fun getAllByClassId(classId: Long): List<FlashCard>

    suspend fun getAllByDeckId(deckId: Long): List<FlashCard>

    suspend fun listAll() : List<FlashCard>

    suspend fun insertAll(flashCards: List<FlashCard>)

    suspend fun deleteAll()

    suspend fun getAll() : List<FlashCard>

    suspend fun insertFlashCard(flashCard: FlashCard): Long

    suspend fun updateFlashCard(flashCard: FlashCard)

    suspend fun updateFlashCards(flashCards: List<FlashCard>): Int

    suspend fun deleteFlashCard(flashCard: FlashCard)

    suspend fun upsertFlashCard(flashCard: FlashCard)

    suspend fun getFlashCardsCountByDeckId(deckId: Long): Int

}