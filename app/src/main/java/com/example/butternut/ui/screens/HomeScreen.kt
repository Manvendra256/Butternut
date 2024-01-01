package com.example.butternut.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Download
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material.icons.rounded.Upload
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.butternut.ui.bottomsheets.AddOrUpdateClassBottomSheet
import com.example.butternut.ui.bottomsheets.ClassBottomSheet
import com.example.butternut.ui.viewmodels.HomeViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    addNewClass: () -> Unit,
    backUpData: () -> Unit,
    restoreData: () -> Unit,
    navigateToClassScreen: (Long) -> Unit,
    navigateToPlayScreen: () -> Unit
) {
    val classes by viewModel.classes.collectAsStateWithLifecycle(initialValue = emptyList())
    val coroutineScope = rememberCoroutineScope()
    val addOrUpdateBottomSheetState: SheetState =
        rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val classBottomSheetState: SheetState =
        rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var showAddOrUpdateClassBottomSheet by remember { mutableStateOf(false) }
//    var startNewActivity by remember { mutableStateOf(false) }
    var showClassBottomSheet by remember { mutableStateOf(false) }
    var currentClass by remember {
        mutableStateOf(com.example.butternut.data.Class(name = ""))
    }
    var showAlertDialog by remember { mutableStateOf(false) }
    var showBackupButtons by remember { mutableStateOf(false) }


    Scaffold(topBar = {
        CenterAlignedTopAppBar(title = {
            Text(
                text = "Butternut",
                modifier = Modifier.combinedClickable(onClick = {}, onLongClick = {
                    showBackupButtons = !showBackupButtons
                })
            )
        })
    },
        floatingActionButton = {
            Column(horizontalAlignment = Alignment.End) {
                if (showBackupButtons) {
                    SmallFloatingActionButton(
                        onClick = backUpData,
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier.padding(bottom = 10.dp)
                    ) {
                        Icon(Icons.Rounded.Upload, contentDescription = "Start Activity")
                    }
                    SmallFloatingActionButton(
                        onClick = restoreData,
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier.padding(bottom = 10.dp)
                    ) {
                        Icon(Icons.Rounded.Download, contentDescription = "Start Activity")
                    }
                }
                SmallFloatingActionButton(
                    onClick = navigateToPlayScreen,
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.padding(bottom = 10.dp)
                ) {
                    Icon(Icons.Rounded.PlayArrow, contentDescription = "Play")
                }
                ExtendedFloatingActionButton(
                    text = { Text(text = "New class") },
                    icon = { Icon(Icons.Rounded.Add, contentDescription = "Insert a new class") },
                    onClick = {
                        currentClass = com.example.butternut.data.Class(name = "")
                        showAddOrUpdateClassBottomSheet = true
                    },
                    shape = RoundedCornerShape(16.dp)
                )
            }
        }) {
        LazyColumn(modifier = Modifier.padding(it)) {
            items(classes, key = { class_ -> class_.classId }) { class_ ->
                ListItem(
                    headlineContent = { Text(text = class_.name) },
                    leadingContent = {
                        Icon(
                            Icons.Filled.Book,
                            contentDescription = "Localized description",
                        )
                    },
                    trailingContent = {
                        IconButton(onClick = {
                            currentClass = class_
                            showClassBottomSheet = true
                        }) {
                            Icon(
                                Icons.Filled.MoreVert,
                                contentDescription = "More Options"
                            )
                        }
                    },
                    tonalElevation = 5.dp,
                    modifier = Modifier
                        .padding(4.dp)
                        .shadow(4.dp, shape = RoundedCornerShape(16.dp))
                        .clickable(onClick = { navigateToClassScreen(class_.classId) })
                )
            }
        }
    }
//    if(startNew)
    if (showAddOrUpdateClassBottomSheet) {
        AddOrUpdateClassBottomSheet(
            viewModel = viewModel,
            sheetState = addOrUpdateBottomSheetState,
            scope = coroutineScope,
            update = currentClass.name.isNotEmpty(),
            class_ = currentClass,
            onDismissRequest = { showAddOrUpdateClassBottomSheet = false })
    }
    if (showClassBottomSheet) {
        ClassBottomSheet(
            sheetState = classBottomSheetState,
            scope = coroutineScope,
            onDismissRequest = { showClassBottomSheet = false },
            editClass = {
                showClassBottomSheet = false
                showAddOrUpdateClassBottomSheet = true
            },
            deleteClass = {
                showClassBottomSheet = false
                coroutineScope.launch {
                    try {
                        viewModel.deleteClass(currentClass)
                    } catch (e: Exception) {
                        showAlertDialog = true
                    }
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
            },
            title = {
                Text(text = "Cannot delete class")
            },
            text = {
                Text(text = "Please delete all the decks in the class before deleting the class")
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


