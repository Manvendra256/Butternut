package com.example.butternut.navigation

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat.startActivity
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.butternut.backup.AppData
import com.example.butternut.backup.BackupImpl
import com.example.butternut.data.FlashCard
import com.example.butternut.ui.screens.*
import com.example.butternut.ui.viewmodels.*
import com.google.gson.Gson
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import javax.inject.Inject
import kotlin.coroutines.coroutineContext

@Composable
fun MyApp(viewModel: HomeViewModel, backUpData: () -> Unit, restoreData: () -> Unit) {
    val navController = rememberNavController()
    val coroutineScope = rememberCoroutineScope()
    val dataHolderViewModel: DataHolderViewModel = hiltViewModel()
    val deckViewModel: DeckViewModel = hiltViewModel()
    NavHost(navController = navController, startDestination = Screen.HomeScreen.route) {
        composable(route = Screen.HomeScreen.route) {
            HomeScreen(
                viewModel,
                addNewClass = { navController.navigate(route = Screen.AddNewClassScreen.route) },
                navigateToClassScreen = { classId -> navController.navigate(route = Screen.ClassScreen.route + "/$classId") },
                navigateToPlayScreen = {
//                    println("navigating to playScreen")
                    navController.navigate(route = Screen.PlayScreen.route) {
                        launchSingleTop = true
                    }
                },
                backUpData = backUpData,
                restoreData = restoreData,
            )
        }
        composable(route = Screen.AddNewClassScreen.route) {
            AddNewClassScreen(viewModel, moveBackToHomeScreen = { navController.popBackStack() })
        }
        composable(route = Screen.AddNewDeckScreen.route + "/{class_id}", arguments = listOf(
            navArgument("class_id") {
                this.type = NavType.LongType
            }
        )) {
            val classViewModel: ClassViewModel = hiltViewModel()
            val classId = it.arguments!!.getLong("class_id")
            AddNewDeckScreen(
                classViewModel,
                moveBackToHomeScreen = { navController.popBackStack() },
                classId = classId
            )
        }
        composable(route = Screen.AddOrUpdateFlashCardScreen.route + "/{class_id}/{deck_id}?flash_card_id={flash_card_id}",
            arguments = listOf(
                navArgument("class_id") {
                    this.type = NavType.LongType
                },
                navArgument("deck_id") {
                    this.type = NavType.LongType
                },
                navArgument("flash_card_id") {
                    this.type = NavType.LongType
                    this.defaultValue = -1L
                }
            )) {
            val deckViewModel: DeckViewModel = hiltViewModel()
            val deckId = it.arguments!!.getLong("deck_id")
            val classId = it.arguments!!.getLong("class_id")
            val flashCardId = it.arguments!!.getLong("flash_card_id")
            val flashCard: FlashCard = if (flashCardId == -1L) FlashCard(
                question = "",
                answer = "",
                confidenceLevel = 3,
                isBookmarked = false,
                deckId = deckId,
                classId = classId
            ) else dataHolderViewModel.flashCard
            AddOrUpdateNewFlashCardScreen(
                deckViewModel,
                flashCard,
                moveBackToHomeScreen = { navController.popBackStack() },
            )
        }
        composable(
            route = Screen.ClassScreen.route + "/{class_id}",
            arguments = listOf(
                navArgument("class_id") {
                    this.type = NavType.LongType
                }
            )
        ) {
            val classViewModel: ClassViewModel = hiltViewModel()
//            Log.d("classViewModel", classViewModel.toString())

            val classId = it.arguments!!.getLong("class_id")
            val decks by classViewModel.getAllByClassId(classId)
                .collectAsStateWithLifecycle(initialValue = emptyList())
            ClassScreen(
                viewModel = classViewModel,
                classId = classId,
                decks = decks,
                navigateToAddNewDeckScreen = {
                    navController.navigate(Screen.AddNewDeckScreen.route + "/$classId")
                },
                deleteDeck = { deck -> coroutineScope.launch { classViewModel.deleteDeck(deck) } },
                navigateToDeckScreen = { deckId ->
                    navController.navigate(route = Screen.ClassScreen.route + "/$classId" + Screen.DeckScreen.route + "/$deckId")
                },
                navigateToPlayScreen = { classId ->
                    navController.navigate(Screen.PlayScreen.route + "?" + Screen.ClassScreen.route + "=$classId")
                }
            )
        }

        composable(
            route = Screen.ClassScreen.route + "/{class_id}" + Screen.DeckScreen.route + "/{deck_id}",
            arguments = listOf(
                navArgument(name = "class_id") {
                    this.type = NavType.LongType
                },
                navArgument(name = "deck_id") {
                    this.type = NavType.LongType
                }
            )
        ) {
            val deckViewModel: DeckViewModel = hiltViewModel()
//            Log.d("deckViewModel", deckViewModel.toString())

            val classId = it.arguments!!.getLong("class_id")
            val deckId = it.arguments!!.getLong("deck_id")
            val flashCards by deckViewModel.getFlashCardsByDeckId(deckId)
                .collectAsStateWithLifecycle(initialValue = emptyList())
            DeckScreen(
                deckId = deckId,
                flashCards = flashCards,
                navigateToAddNewFlashCardScreen = { navController.navigate(Screen.AddOrUpdateFlashCardScreen.route + "/$classId/$deckId") },
                deleteFlashcard = { flashCard ->
                    coroutineScope.launch {
                        deckViewModel.deleteFlashCard(
                            flashCard
                        )
                    }
                },
                navigateToFlashCardScreen = { flashCardId ->
//                    Log.d(
//                        "Navigation",
//                        "Navigating to flashCard Screen with flashcardId = $flashCardId"
//                    )
                    navController.navigate(Screen.FlashCardScreen.route + "/$flashCardId")
                },
                navigateToPlayScreen = { deckId ->
                    navController.navigate(Screen.PlayScreen.route + "?" + Screen.DeckScreen.route + "=$deckId")
                }
            )
        }
        composable(route = Screen.PlayScreen.route + "?" + Screen.ClassScreen.route + "={class_id}&" + Screen.DeckScreen.route + "={deck_id}&" + "is_bookmarked={is_bookmarked}",
            arguments = listOf(
                navArgument("class_id") {
                    this.type = NavType.LongType
                    this.defaultValue = -1
                },
                navArgument("deck_id") {
                    this.type = NavType.LongType
                    this.defaultValue = -1
                },
                navArgument("is_bookmarked") {
                    this.type = NavType.BoolType
                    this.defaultValue = false
                }
            )
        ) {
            val coroutineScope = rememberCoroutineScope()
            val classId = it.arguments!!.getLong("class_id")
            val deckId = it.arguments!!.getLong("deck_id")
            val isBookmarked = it.arguments!!.getBoolean("is_bookmarked")
            val flashCardViewModel: FlashCardViewModel = hiltViewModel()
//            Log.d("flashCardViewModel", classId.toString())
            val flashcard by flashCardViewModel.uiState.collectAsStateWithLifecycle(initialValue = initialFlashCardValue)
            var showAlertDialog by remember { mutableStateOf(false) }

            LaunchedEffect(Unit) {
//                println("Only logged once inside launched effect $classId $deckId $isBookmarked")
                if (!flashCardViewModel.initialize(classId, deckId, isBookmarked)) showAlertDialog =
                    true
            }

            if (flashcard != null && flashcard.flashCardId != -1L) {
                PlayScreen(
                    flashCard = flashcard,
                    updateFlashCardAndShowNewOne = { flashCard ->
                        coroutineScope.launch {
                            flashCardViewModel.getRandomFlashCard()
                            flashCardViewModel.updateFlashCard(flashCard)
                        }
                    },
                    readOnly = false,
                    goToUpdateFlashCardScreen = {
                        dataHolderViewModel.flashCard = flashcard
                        navController.navigate(route = Screen.AddOrUpdateFlashCardScreen.route + "/$classId/$deckId?flash_card_id=${flashcard.flashCardId}") },
                    deleteFlashCard = {
                        coroutineScope.launch {
                            flashCardViewModel.deleteFlashCard(flashcard)
                            if (!flashCardViewModel.getRandomFlashCard()) showAlertDialog = true
//                            else Log.d("temp", "I should be executed")
                        }
                    }
                )
            }
            if (showAlertDialog) {
                AlertDialog(
                    onDismissRequest = {
                        // Dismiss the dialog when the user clicks outside the dialog or on the back
                        // button. If you want to disable that functionality, simply use an empty
                        // onDismissRequest.
                        showAlertDialog = false
                        navController.popBackStack()
                    },
                    title = {
                        Text(text = "No flashcards")
                    },
                    text = {
                        Text(text = "Please ensure that there are flashcards in the class/deck before playing")
                    },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                showAlertDialog = false
                                navController.popBackStack()
                            }
                        ) {
                            Text("Ok")
                        }
                    }
                )

            }
        }
        composable(route = Screen.FlashCardScreen.route + "/{flash_card_id}", arguments = listOf(
            navArgument("flash_card_id") {
                this.type = NavType.LongType
                this.defaultValue = -1
            }
        )) {
            val flashCardViewModel: FlashCardViewModel = hiltViewModel()
            val flashCardId = it.arguments!!.getLong("flash_card_id")
            val flashCard by flashCardViewModel.getFlashCardByFlashCardId(flashCardId = flashCardId)
                .collectAsStateWithLifecycle(
                    initialValue = initialFlashCardValue
                )
            if (flashCard.flashCardId != -1L) {
                PlayScreen(
                    flashCard = flashCard,
                    updateFlashCardAndShowNewOne = {},
                    readOnly = true,
                    goToUpdateFlashCardScreen = {
                        dataHolderViewModel.flashCard = flashCard
                        navController.navigate(route = Screen.AddOrUpdateFlashCardScreen.route + "/${flashCard.classId}/${flashCard.deckId}?flash_card_id=${flashCard.flashCardId}")
                    },
                    deleteFlashCard = {
                        navController.popBackStack()
                        deckViewModel.deleteFlashCard(flashCard)
                    }
                )
            }
        }
    }
}

val initialFlashCardValue = FlashCard(
    flashCardId = -1,
    question = "Initial Value",
    answer = "Initial Value",
    isBookmarked = false,
    classId = 1,
    deckId = 1,
    confidenceLevel = 1
)