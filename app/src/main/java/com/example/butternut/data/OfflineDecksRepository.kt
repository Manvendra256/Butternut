package com.example.butternut.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class OfflineDecksRepository @Inject constructor(private val deckDao: DeckDao): DecksRepository{
    override fun getAll() : Flow<List<Deck>> = deckDao.getAll()
    override fun getDecksByClassId(classId: Long): Flow<List<Deck>> = deckDao.getDecksByClassId(classId)
    override suspend fun listAll(): List<Deck> = withContext(Dispatchers.IO) {deckDao.listAll()}
    override suspend fun insertAll(decks: List<Deck>) = withContext(Dispatchers.IO) {deckDao.insertAll(decks) }
    override suspend fun deleteAll()  = withContext(Dispatchers.IO) {deckDao.deleteAll()}
    override suspend fun getDecksCountByClassId(classId: Long): Int = withContext(Dispatchers.IO) {deckDao.getDecksCountByClassId(classId)}
    override suspend fun insertDeck(deck: Deck) : Long = withContext(Dispatchers.IO) {deckDao.insertDeck(deck)}
    override suspend fun deleteDeck(deck: Deck) = withContext(Dispatchers.IO) {deckDao.deleteDeck(deck)}
    override suspend fun updateDeck(deck: Deck) = withContext(Dispatchers.IO) {deckDao.updateDeck(deck)}
}