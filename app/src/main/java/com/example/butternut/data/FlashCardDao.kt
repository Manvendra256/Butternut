package com.example.butternut.data

import android.database.Cursor
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface FlashCardDao {
    @Query("SELECT * FROM flash_cards Where id = :flashCardId")
    fun getByFlashCardId(flashCardId: Long) : Flow<FlashCard>

    @Query("SELECT * FROM flash_cards WHERE deck_id = :deckId")
    fun getFlashCardsByDeckId(deckId: Long): Flow<List<FlashCard>>

    @Query("SELECT * FROM flash_cards")
    suspend fun listAll() : List<FlashCard>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(flashCards: List<FlashCard>)

    @Query("DELETE FROM flash_cards")
    suspend fun deleteAll()

    @Query("SELECT * FROM flash_cards")
    suspend fun getAll() : List<FlashCard>

    @Query("SELECT * FROM flash_cards WHERE is_bookmarked = true")
    suspend fun getAllBookmarked() : List<FlashCard>

    @Query("SELECT * FROM flash_cards WHERE deck_id = :deckId")
    suspend fun getAllByDeckId(deckId: Long): List<FlashCard>

    @Query("SELECT * FROM flash_cards WHERE class_id = :classId")
    suspend fun getAllByClassId(classId: Long): List<FlashCard>

    @Query("SELECT COUNT(*) FROM flash_cards WHERE deck_id = :deckId")
    suspend fun getFlashCardsCountByDeckId(deckId: Long): Int

    @Insert
    suspend fun insertFlashCard(flashCard: FlashCard): Long

    @Update
    suspend fun updateFlashCard(flashCard: FlashCard)

    @Update
    suspend fun updateFlashCards(flashCards: List<FlashCard>): Int

    @Delete
    suspend fun deleteFlashCard(flashCard: FlashCard)

    @Upsert
    suspend fun upsertFlashCard(flashCard: FlashCard)
}