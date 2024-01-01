package com.example.butternut.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "flash_cards",
    indices = [Index("deck_id"), Index("class_id")],
    foreignKeys = [
        ForeignKey(entity = Deck::class, parentColumns = ["id"], childColumns = ["deck_id"]),
        ForeignKey(entity = Class::class, parentColumns = ["id"], childColumns = ["class_id"])
    ]
)
data class FlashCard(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val flashCardId: Long = 0,
    @ColumnInfo(name ="question") val question: String,
    @ColumnInfo(name = "answer") val answer: String,
    @ColumnInfo(name = "confidence_level") val confidenceLevel: Int,
    @ColumnInfo(name = "is_bookmarked") val isBookmarked: Boolean,
    @ColumnInfo(name = "deck_id") val deckId: Long,
    @ColumnInfo(name = "class_id") val classId: Long,
)
