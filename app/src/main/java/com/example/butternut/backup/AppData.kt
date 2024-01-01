package com.example.butternut.backup

import com.example.butternut.data.Deck
import com.example.butternut.data.FlashCard

data class AppData(
    val classes: List<com.example.butternut.data.Class>,
    val decks: List<Deck>,
    val flashCards: List<FlashCard>,
)
