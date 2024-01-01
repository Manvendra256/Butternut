package com.example.butternut

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat.startActivity
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import com.example.butternut.backup.AppData
import com.example.butternut.backup.BackupImpl
import com.example.butternut.navigation.MyApp
import com.example.butternut.ui.theme.ButternutTheme
import com.example.butternut.ui.viewmodels.DeckViewModel
import com.example.butternut.ui.viewmodels.HomeViewModel
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import java.io.BufferedReader
import java.io.InputStreamReader
import javax.inject.Inject
import kotlin.coroutines.coroutineContext

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val homeViewModel: HomeViewModel by viewModels()

    // delete this after testing
    private val deckViewModel: DeckViewModel by viewModels()

    @Inject
    lateinit var backupImpl: BackupImpl

    //'getContent' is an 'ActivityResultLauncher'
    private val getContent =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            // 'ActivityResultCallback': Handle the returned Uri
            try {
                lifecycleScope.launch(Dispatchers.IO) {
                    val gson = Gson()
                    backupImpl.clearAndReInitializeDatabase(
                        gson.fromJson(
                            BufferedReader(
                                InputStreamReader(contentResolver.openInputStream(uri!!))
                            ).readText(), AppData::class.java
                        )
                    )
                }
            } catch (e: Exception) {
                Log.d("Error while parsing file", e.message!!)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            ButternutTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier
                        .fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    var reinitializeDB by remember { mutableStateOf(false) }
                    var storeDB by remember { mutableStateOf(false) }


                    if (reinitializeDB) {
                        getContent.launch("text/plain")
                        reinitializeDB = false
                    }
                    if (storeDB) {
                        val sendIntent = runBlocking { withContext(Dispatchers.IO){ backupImpl.getIntent()} }
                        startActivity(sendIntent)
                        storeDB = false
                    }

                    MyApp(
                        homeViewModel,
                        backUpData = { storeDB = true },
                        restoreData = { reinitializeDB = true })
//                    val question = LoremIpsum(20)
//                    val answer = LoremIpsum(200)
//                    val flashCard: FlashCard = FlashCard(
//                        question = question.values.first(),
//                        answer = answer.values.first(),
//                        confidenceLevel = 1,
//                        classId = 1,
//                        deckId = 1,
//                        weight = 1,
//                        isBookmarked = true
//                    )
//                    PlayScreen(flashCard, { flashCard -> {} })
                }
            }
        }
    }
}

/*
* 1. Handle error when deleting a class or deck which has flashcards
* 2. UI for add/update new class/deck/flashcard
* 3. Remove logs when done and unused params in functions as well.
* 4. Implement Icon picker for bottom sheet in AddClassBottomSheet and other use cases?
* */
//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview() {
//    ButternutTheme {
//        Greeting("Android", flashCardViewModel)
//    }
//}
