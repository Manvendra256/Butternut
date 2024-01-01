package com.example.butternut.backup

import android.content.Intent
import android.util.Log
import androidx.compose.ui.platform.LocalContext
import com.example.butternut.data.ClassDao
import com.example.butternut.data.DeckDao
import com.example.butternut.data.FlashCardDao
import com.google.gson.Gson
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.system.measureTimeMillis

@Singleton
class BackupImpl @Inject constructor(
    private val classDao: ClassDao,
    private val deckDao: DeckDao,
    private val flashCardDao: FlashCardDao
) : Backup {
    private val gson = Gson()

    override suspend fun getIntent() :Intent {
        val appDataJson: String = gson.toJson(AppData(
            classes = classDao.listAll(),
            decks = deckDao.listAll(),
            flashCards = flashCardDao.listAll()
        ))
        return Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, appDataJson)
            type = "text/json"
        }
    }

    override suspend fun deleteDataFromDatabase() {
//        Log.d("deleteDataFromDatabase()", "Deleting data from DB")
        flashCardDao.deleteAll();
        deckDao.deleteAll();
        classDao.deleteAll();
//        Log.d("deleteDataFromDatabase()", "Deleted data from DB")
    }

    override suspend fun insertDataInDatabase(appData: AppData) {
//        Log.d("InsertDataInDatabase()", "Inserting data in DB")
        classDao.insertAll(appData.classes)
        deckDao.insertAll(appData.decks)
        flashCardDao.insertAll(appData.flashCards)
//        Log.d("InsertDataInDatabase()", "Data inserted")
    }

    override suspend fun clearAndReInitializeDatabase(appData: AppData) {
        deleteDataFromDatabase()
        insertDataInDatabase(appData)
    }
}

