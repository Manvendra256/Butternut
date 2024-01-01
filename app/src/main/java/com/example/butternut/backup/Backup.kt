package com.example.butternut.backup

import android.content.Intent
import com.example.butternut.data.Class
import kotlinx.coroutines.flow.Flow

interface Backup {

    suspend fun getIntent() : Intent

    suspend fun deleteDataFromDatabase()

    suspend fun insertDataInDatabase(appData: AppData)

    suspend fun clearAndReInitializeDatabase(appData: AppData)

}