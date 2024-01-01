package com.example.butternut.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoFixHigh
import androidx.compose.material.icons.filled.BookmarkAdded
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.butternut.data.FlashCard
import com.example.butternut.ui.bottomsheets.FlashCardBottomSheet
import com.example.butternut.ui.theme.Typography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayScreen(
    flashCard: FlashCard,
    updateFlashCardAndShowNewOne: (FlashCard) -> Unit,
    readOnly: Boolean,
    goToUpdateFlashCardScreen: () -> Unit,
    deleteFlashCard: () -> Unit
) {
    val question = flashCard.question;
    val answer = flashCard.answer;
    var showAnswer by rememberSaveable {
        mutableStateOf(readOnly)
    }
    val coroutineScope = rememberCoroutineScope()
    val bottomSheetState: SheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var showBottomSheet by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.8f)
                .shadow(5.dp, shape = RoundedCornerShape(16.dp))
                .verticalScroll(rememberScrollState())
        ) {
            Row() {
                Spacer(modifier = Modifier.width(30.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Row(
                        modifier = Modifier
                            .padding(
                                top = 30.dp,
                                bottom = 10.dp
                            )
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Question",
                            textAlign = TextAlign.Justify,
                            style = Typography.titleLarge,
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        if (flashCard.isBookmarked) {
                            Icon(
                                Icons.Filled.BookmarkAdded,
                                contentDescription = "Play"
                            )
                        }
                        IconButton(onClick = { showBottomSheet = true }) {
                            Icon(
                                Icons.Filled.MoreVert,
                                contentDescription = "Play"
                            )
                        }
                    }
                    Text(
                        text = question,
                        textAlign = TextAlign.Justify,
                        modifier = Modifier.padding(bottom = 30.dp)
                    )
                    AnimatedVisibility(
                        visible = showAnswer,
                        enter = slideInVertically(initialOffsetY = { it }),
                        exit = slideOutVertically(targetOffsetY = { it })
                    ) {
                        Column() {
                            Divider()
                            Text(
                                text = "Answer",
                                textAlign = TextAlign.Justify,
                                style = Typography.titleLarge,
                                modifier = Modifier.padding(
                                    top = 30.dp,
                                    bottom = 10.dp
                                )
                            )
                            Text(
                                text = answer,
                                textAlign = TextAlign.Justify,
                                modifier = Modifier.padding(bottom = 30.dp)
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.width(30.dp))
            }
        }
        if (!showAnswer && !readOnly) {
            ElevatedButton(
                onClick = { showAnswer = !showAnswer },
            ) {
                Icon(Icons.Filled.AutoFixHigh, contentDescription = "Magic Wand")
                Spacer(modifier = Modifier.width(10.dp))
                Text(text = "Reveal Answer")
            }
        } else if (!readOnly) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                for (i in 1..5) {
                    ElevatedButton(
                        onClick = {
                            showAnswer = !showAnswer
                            updateFlashCardAndShowNewOne(flashCard.copy(confidenceLevel = i))
                        },
                        shape = CircleShape
                    ) {
                        Text(text = i.toString())
                    }
                }
            }
        }
    }
    if (showBottomSheet) {
        FlashCardBottomSheet(
            sheetState = bottomSheetState,
            scope = coroutineScope,
            onDismissRequest = { showBottomSheet = false },
            goToUpdateFlashCardScreen = {
                showBottomSheet = false
                goToUpdateFlashCardScreen()
            },
            deleteFlashCard = {
                showBottomSheet = false
                deleteFlashCard()
            }
        )
    }
}