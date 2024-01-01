package com.example.butternut.data

import kotlinx.coroutines.flow.Flow

interface DecksRepository {
    fun getAll() : Flow<List<Deck>>

    fun getDecksByClassId(classId : Long) : Flow<List<Deck>>

    suspend fun listAll() : List<Deck>

    suspend fun insertAll(decks: List<Deck>)

    suspend fun deleteAll()

    suspend fun getDecksCountByClassId(classId: Long): Int

    suspend fun insertDeck(deck: Deck) : Long

    suspend fun deleteDeck(deck: Deck)

    suspend fun updateDeck(deck: Deck)
}