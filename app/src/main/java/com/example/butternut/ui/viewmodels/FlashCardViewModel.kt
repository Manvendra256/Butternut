package com.example.butternut.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.butternut.data.FlashCard
import com.example.butternut.data.FlashCardsRepository
import com.example.butternut.di.OfflineRepo
import com.example.butternut.navigation.initialFlashCardValue
import com.example.butternut.util.FenwickTree
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FlashCardViewModel @Inject constructor(@OfflineRepo private val flashCardsRepository: FlashCardsRepository) :
    ViewModel() {

    // make it private after debugging
    lateinit var flashCards: Array<Pair<Long, Int>>
    lateinit var fenwickTree: FenwickTree
    private val _uiState = MutableStateFlow(initialFlashCardValue)
    val uiState: StateFlow<FlashCard> = _uiState.asStateFlow()
    var randomIndex: Int = 0
    var job: Job = viewModelScope.launch { }


    suspend fun initialize(classId: Long, deckId: Long, isBookmarked: Boolean): Boolean {
        if (classId != -1L) {
            flashCards = flashCardsRepository.getAllByClassId(classId)
                .map { flashCard -> Pair(flashCard.flashCardId, flashCard.confidenceLevel) }
                .sortedBy { pair -> pair.first }.toTypedArray()
        } else if (deckId != -1L) {
            flashCards = flashCardsRepository.getAllByDeckId(deckId)
                .map { flashCard -> Pair(flashCard.flashCardId, flashCard.confidenceLevel) }
                .sortedBy { pair -> pair.first }.toTypedArray()
        } else if (isBookmarked) {
            flashCards = flashCardsRepository.getAllBookmarked()
                .map { flashCard -> Pair(flashCard.flashCardId, flashCard.confidenceLevel) }
                .sortedBy { pair -> pair.first }.toTypedArray()
        } else {
            flashCards = flashCardsRepository.getAll()
                .map { flashCard -> Pair(flashCard.flashCardId, flashCard.confidenceLevel) }
                .sortedBy { pair -> pair.first }.toTypedArray()
        }
        fenwickTree = FenwickTree(flashCards)
        return getRandomFlashCard()
    }


    // update in fenwickTree, flashCards, and Database
    fun updateFlashCard(flashCard: FlashCard) {
        viewModelScope.launch {
            var l: Int = 0
            var r: Int = flashCards.size - 1
            var m: Int = -1
            while (l <= r) {
                m = (l + r) / 2
                if (flashCards[m].first == flashCard.flashCardId) break
                else if (flashCards[m].first < flashCard.flashCardId) l = m + 1
                else r = m - 1;
            }
//            Log.d("Value of m", m.toString() + " " + flashCards[m].first.toString() + " " + flashCard.flashCardId.toString()  )
            assert(flashCards[m].first == flashCard.flashCardId)
            if (flashCards[m].second != flashCard.confidenceLevel) {
                fenwickTree.add(m, flashCards[m].second - flashCard.confidenceLevel)
                flashCards[m] = flashCards[m].copy(second = flashCard.confidenceLevel)
            }
            flashCardsRepository.updateFlashCard(flashCard)
        }
    }

    suspend fun getRandomFlashCard(): Boolean {
        val r = (1..100000).random()
        randomIndex = fenwickTree.find_random()
        if (randomIndex == -1) return false
//        Log.d("index", randomIndex.toString())
        job.cancel()
        job = viewModelScope.launch {
            flashCardsRepository.getByFlashCardId(flashCards[randomIndex].first)
                .collect { flashCard ->
//                    Log.d("Emitting flow", "Collecting from getRandomFlashCard() $r $randomIndex")
                    _uiState.value = flashCard
                }
        }
        return true
    }

    suspend fun deleteFlashCard(flashCard: FlashCard) {
        var l: Int = 0
        var r: Int = flashCards.size - 1
        var m: Int = -1
        while (l <= r) {
            m = (l + r) / 2
            if (flashCards[m].first == flashCard.flashCardId) break
            else if (flashCards[m].first < flashCard.flashCardId) l = m + 1
            else r = m - 1;
        }
//            Log.d("Value of m", m.toString() + " " + flashCards[m].first.toString() + " " + flashCard.flashCardId.toString()  )
        assert(flashCards[m].first == flashCard.flashCardId)
        fenwickTree.add(m, flashCards[m].second - 6)
        flashCards[m] = flashCards[m].copy(second = 0)
        flashCardsRepository.deleteFlashCard(flashCard)
    }

    fun setToInitialValue() {
        _uiState.value = initialFlashCardValue
    }

    fun getFlashCardByFlashCardId(flashCardId: Long): Flow<FlashCard> =
        flashCardsRepository.getByFlashCardId(flashCardId)

    override fun onCleared() {
//        Log.d("View model", "FlashCard View model destroyed")
        super.onCleared()
    }
}