package com.example.butternut.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "decks",
    indices = [Index("class_id")],
    foreignKeys = [ForeignKey(Class::class, parentColumns = ["id"], childColumns = ["class_id"])]
)
data class Deck(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val deckId: Long =0,
    @ColumnInfo(name = "class_id")val classId: Long,
    @ColumnInfo(name = "name") val name: String,
)
