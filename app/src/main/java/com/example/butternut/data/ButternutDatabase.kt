package com.example.butternut.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Class::class, Deck::class, FlashCard::class], version = 1, exportSchema = false)
abstract class ButternutDatabase : RoomDatabase() {

    abstract fun classDao(): ClassDao

    abstract fun deckDao(): DeckDao

    abstract fun flashCardDao(): FlashCardDao

    companion object {
        @Volatile
        private var Instance: ButternutDatabase? = null

        fun getDatabase(context: Context) : ButternutDatabase {
            return Instance ?: synchronized(this) {
            Room.databaseBuilder(context, ButternutDatabase::class.java, "item_database")
                .fallbackToDestructiveMigration()
                .build()
                .also{ Instance = it}}
        }
    }
}