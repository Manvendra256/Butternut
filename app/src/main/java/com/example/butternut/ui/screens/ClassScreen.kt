package com.example.butternut.ui.screens

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import com.example.butternut.data.Deck
import com.example.butternut.ui.bottomsheets.AddOrUpdateDeckBottomSheet
import com.example.butternut.ui.bottomsheets.DeckBottomSheet
import com.example.butternut.ui.viewmodels.ClassViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClassScreen(
    viewModel: ClassViewModel,
    classId: Long = 0,
    decks: List<Deck>,
    navigateToAddNewDeckScreen: () -> Unit,
    deleteDeck: (Deck) -> Unit,
    navigateToDeckScreen: (Long) -> Unit,
    navigateToPlayScreen: (Long) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val addOrUpdateBottomSheetState: SheetState =
        rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val deckBottomSheetState: SheetState =
        rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var showAddOrUpdateDeckBottomSheet by remember { mutableStateOf(false) }
    var showDeckBottomSheet by remember { mutableStateOf(false) }
    var currentDeck by remember {
        mutableStateOf(com.example.butternut.data.Deck(name = "", classId = classId))
    }
    var showAlertDialog by remember { mutableStateOf(false) }


    Scaffold(topBar = { CenterAlignedTopAppBar(title = { Text("Butternut") }) },
        floatingActionButton = {
            Column(horizontalAlignment = Alignment.End) {
                SmallFloatingActionButton(
                    onClick = { navigateToPlayScreen(classId) },
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.padding(bottom = 10.dp)
                ) {
                    Icon(Icons.Rounded.PlayArrow, contentDescription = "Play")
                }
                ExtendedFloatingActionButton(
                    text = { Text(text = "New deck") },
                    icon = { Icon(Icons.Rounded.Add, contentDescription = "Insert a new deck") },
                    onClick = {
                        currentDeck = com.example.butternut.data.Deck(name = "", classId = classId)
                        showAddOrUpdateDeckBottomSheet = true
                    },
                    shape = RoundedCornerShape(16.dp)
                )
            }
        }) {
        LazyColumn(modifier = Modifier.padding(it)) {
            items(decks, key = { deck -> deck.deckId }) { deck ->
                ListItem(
                    headlineContent = { Text(text = deck.name) },
                    leadingContent = {
                        Icon(
                            Icons.Filled.Book,
                            contentDescription = "Localized description",
                        )
                    },
                    trailingContent = {
                        IconButton(onClick = {
                            currentDeck = deck
                            showDeckBottomSheet = true
                        }) {
                            Icon(
                                Icons.Filled.MoreVert,
                                contentDescription = "More options"
                            )
                        }
                    },
                    tonalElevation = 5.dp,
                    modifier = Modifier
                        .padding(4.dp)
                        .shadow(4.dp, shape = RoundedCornerShape(16.dp))
                        .clickable(onClick = { navigateToDeckScreen(deck.deckId) })
                )
            }
        }
    }
    if (showAddOrUpdateDeckBottomSheet) {
        AddOrUpdateDeckBottomSheet(
            viewModel = viewModel,
            sheetState = addOrUpdateBottomSheetState,
            scope = coroutineScope,
            update = currentDeck.name.isNotEmpty(),
            deck = currentDeck,
            onDismissRequest = { showAddOrUpdateDeckBottomSheet = false })
    }
    if (showDeckBottomSheet) {
        DeckBottomSheet(
            sheetState = deckBottomSheetState,
            scope = coroutineScope,
            onDismissRequest = { showDeckBottomSheet = false },
            editDeck = {
                showDeckBottomSheet = false
                showAddOrUpdateDeckBottomSheet = true
            },
            deleteDeck = {
                showDeckBottomSheet = false
                coroutineScope.launch {
                    try {
                        viewModel.deleteDeck(currentDeck)
                    } catch(e: Exception){
                        showAlertDialog = true
                    }
                }
            }
        )
    }
    if(showAlertDialog){
        AlertDialog(
            onDismissRequest = {
                // Dismiss the dialog when the user clicks outside the dialog or on the back
                // button. If you want to disable that functionality, simply use an empty
                // onDismissRequest.
                showAlertDialog = false
            },
            title = {
                Text(text = "Cannot delete deck")
            },
            text = {
                Text(text = "Please delete all the flashcards in the deck before deleting the deck")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        showAlertDialog = false
                    }
                ) {
                    Text("Ok")
                }
            }
        )
    }
}