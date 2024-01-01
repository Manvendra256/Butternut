package com.example.butternut.data

import android.database.Cursor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OfflineFlashCardsRepository @Inject constructor(private val flashCardDao: FlashCardDao): FlashCardsRepository {

    override fun getByFlashCardId(flashCardId: Long) : Flow<FlashCard> = flashCardDao.getByFlashCardId(flashCardId)

    override fun getFlashCardsByDeckId(deckId: Long): Flow<List<FlashCard>> = flashCardDao.getFlashCardsByDeckId(deckId)

    override suspend fun listAll(): List<FlashCard> = withContext(Dispatchers.IO) {flashCardDao.listAll()}

    override suspend fun insertAll(flashCards: List<FlashCard>) = withContext(Dispatchers.IO) {flashCardDao.insertAll(flashCards)}

    override suspend fun deleteAll() = withContext(Dispatchers.IO) {flashCardDao.deleteAll()}

    override suspend fun getAllBookmarked(): List<FlashCard> = withContext(Dispatchers.IO) {flashCardDao.getAllBookmarked()}

    override suspend fun getAllByDeckId(deckId: Long): List<FlashCard> = withContext(Dispatchers.IO) {flashCardDao.getAllByDeckId(deckId)}

    override suspend fun getAllByClassId(classId: Long): List<FlashCard> = withContext(Dispatchers.IO) {flashCardDao.getAllByClassId(classId)}

    override suspend fun getAll() : List<FlashCard> = withContext(Dispatchers.IO) {flashCardDao.getAll()}

    override suspend fun insertFlashCard(flashCard: FlashCard): Long = withContext(Dispatchers.IO) {flashCardDao.insertFlashCard(flashCard)}

    override suspend fun updateFlashCard(flashCard: FlashCard) = withContext(Dispatchers.IO) {flashCardDao.updateFlashCard(flashCard)}

    override suspend fun updateFlashCards(flashCards: List<FlashCard>) = withContext(Dispatchers.IO) {flashCardDao.updateFlashCards(flashCards)}

    override suspend fun deleteFlashCard(flashCard: FlashCard) = withContext(Dispatchers.IO) {flashCardDao.deleteFlashCard(flashCard)}

    override suspend fun upsertFlashCard(flashCard: FlashCard) = withContext(Dispatchers.IO) {flashCardDao.upsertFlashCard(flashCard)}

    override suspend fun getFlashCardsCountByDeckId(deckId: Long): Int = withContext(Dispatchers.IO) {flashCardDao.getFlashCardsCountByDeckId(deckId)}
}