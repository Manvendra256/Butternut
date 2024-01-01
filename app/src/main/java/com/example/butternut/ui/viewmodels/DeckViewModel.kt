package com.example.butternut.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.butternut.data.DecksRepository
import com.example.butternut.data.FlashCard
import com.example.butternut.data.FlashCardsRepository
import com.example.butternut.di.OfflineRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeckViewModel @Inject constructor(@OfflineRepo private val flashCardsRepository: FlashCardsRepository) :ViewModel() {

    fun getFlashCardsByDeckId(deckId: Long): Flow<List<FlashCard>> = flashCardsRepository.getFlashCardsByDeckId(deckId)

    fun insertFlashCard(flashCard: FlashCard) = viewModelScope.launch { flashCardsRepository.insertFlashCard(flashCard) }

    fun deleteFlashCard(flashCard: FlashCard) = viewModelScope.launch { flashCardsRepository.deleteFlashCard(flashCard) }

    fun upsertFlashCard(flashCard: FlashCard) = viewModelScope.launch { flashCardsRepository.upsertFlashCard(flashCard) }

    fun updateFlashCard(flashCard: FlashCard) = viewModelScope.launch { flashCardsRepository.updateFlashCard(flashCard) }
}