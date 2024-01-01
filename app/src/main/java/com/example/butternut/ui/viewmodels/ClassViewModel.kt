package com.example.butternut.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.butternut.data.Deck
import com.example.butternut.data.DecksRepository
import com.example.butternut.data.FlashCardsRepository
import com.example.butternut.di.OfflineRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ClassViewModel @Inject constructor(
    @OfflineRepo private val decksRepository: DecksRepository,
    @OfflineRepo private val flashCardsRepository: FlashCardsRepository
) : ViewModel() {
    private val _decks: MutableStateFlow<List<Deck>> = MutableStateFlow(emptyList())
    val decks: StateFlow<List<Deck>> = _decks

    init {
        viewModelScope.launch {
            decksRepository.getAll().collect() { decks ->
                _decks.value = decks
            }
        }
    }

    fun getAllByClassId(classId: Long): Flow<List<Deck>> =
        decksRepository.getDecksByClassId(classId = classId)


    suspend fun insertDeck(deck: Deck) {
        viewModelScope.launch {
            decksRepository.insertDeck(deck)
        }
    }

    suspend fun updateDeck(deck: Deck) {
        viewModelScope.launch {
            decksRepository.updateDeck(deck)
        }
    }

    suspend fun deleteDeck(deck: Deck) {
        if (flashCardsRepository.getFlashCardsCountByDeckId(deckId = deck.deckId) == 0) {
            viewModelScope.launch {
                decksRepository.deleteDeck(deck)
            }
        } else throw IllegalArgumentException()
    }
}
