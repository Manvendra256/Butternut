package com.example.butternut.ui.screens

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material.icons.rounded.QuestionMark
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.butternut.data.FlashCard
import com.example.butternut.ui.theme.KarlaFontFamily
import com.example.butternut.ui.theme.MontserratFontFamily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeckScreen(
    deckId: Long = 0,
    flashCards: List<FlashCard>,
    navigateToAddNewFlashCardScreen: () -> Unit,
    deleteFlashcard: (FlashCard) -> Unit,
    navigateToFlashCardScreen: (Long) -> Unit,
    navigateToPlayScreen: (Long) -> Unit
) {
    Scaffold(topBar = { CenterAlignedTopAppBar(title = { Text("Butternut") }) },
        floatingActionButton = {
            Column(horizontalAlignment = Alignment.End) {
                SmallFloatingActionButton(
                    onClick = {navigateToPlayScreen(deckId)},
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.padding(bottom = 10.dp)
                ) {
                    Icon(Icons.Rounded.PlayArrow, contentDescription = "Play")
                }
            ExtendedFloatingActionButton(
                text = { Text(text = "New flashcard") },
                icon = { Icon(Icons.Rounded.Add, contentDescription = "Insert a new flashcard") },
                onClick = navigateToAddNewFlashCardScreen,
                shape = RoundedCornerShape(16.dp)
            )}
        }) {
        LazyColumn(modifier = Modifier.padding(it)) {
            items(flashCards, key = { flashcard -> flashcard.flashCardId }) { flashCard ->
                flashCardItem(
                    flashCardId = flashCard.flashCardId,
                    question = flashCard.question,
                    answer = flashCard.answer,
                    confidenceLevel = flashCard.confidenceLevel,
                    navigateToFlashCardScreen = navigateToFlashCardScreen
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun flashCardItem(
    flashCardId: Long,
    question: String,
    answer: String,
    confidenceLevel: Int,
    navigateToFlashCardScreen: (Long) -> Unit
) {
    ElevatedCard(
        onClick = {
            navigateToFlashCardScreen(flashCardId)},
        modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
    ) {
        Row(modifier = Modifier.padding(5.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(
                Icons.Rounded.QuestionMark,
                contentDescription = "asd",
                modifier = Modifier.size(64.dp)
            )
            Column {
                Text(
                    modifier = Modifier.padding(bottom = 5.dp),
                    fontWeight = FontWeight.Medium,
                    fontFamily = MontserratFontFamily,
                    fontSize = 16.sp,
                    lineHeight = 24.sp,
                    text = question,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2
                )
                Text(
                    fontWeight = FontWeight.ExtraLight,
                    fontFamily = KarlaFontFamily,
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                    text = answer,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2
                )
            }
        }
    }

}