package com.example.butternut.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.example.butternut.data.Class
import com.example.butternut.data.Deck
import com.example.butternut.data.FlashCard

class DataHolderViewModel(): ViewModel() {
    lateinit var flashCard : FlashCard;
    lateinit var deck: Deck;
    lateinit var class_: Class;
}