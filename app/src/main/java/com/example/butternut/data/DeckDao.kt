package com.example.butternut.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface DeckDao {
    @Query("SELECT * FROM decks ORDER BY name")
    fun getAll() : Flow<List<Deck>>

    @Query("SELECT * FROM decks WHERE class_id = :classId")
    fun getDecksByClassId(classId: Long): Flow<List<Deck>>

    @Query("SELECT * FROM decks")
    suspend fun listAll() : List<Deck>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(decks: List<Deck>)

    @Query("DELETE FROM decks")
    suspend fun deleteAll()

    @Query("SELECT COUNT(*) FROM decks WHERE class_id = :classId")
    suspend fun getDecksCountByClassId(classId: Long): Int

    @Insert
    suspend fun insertDeck(deck: Deck) : Long

    @Delete
    suspend fun deleteDeck(deck: Deck)

    @Update
    suspend fun updateDeck(deck: Deck)
}