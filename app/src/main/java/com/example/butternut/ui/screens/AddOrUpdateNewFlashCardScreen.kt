package com.example.butternut.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BookmarkAdd
import androidx.compose.material.icons.filled.BookmarkAdded
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.butternut.data.FlashCard
import com.example.butternut.ui.theme.Typography
import com.example.butternut.ui.viewmodels.DeckViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddOrUpdateNewFlashCardScreen(
    viewModel: DeckViewModel,
    flashCard: FlashCard,
    moveBackToHomeScreen: () -> Unit
) {
    var question by rememberSaveable {
        mutableStateOf(flashCard.question)
    }
    var answer by rememberSaveable {
        mutableStateOf(flashCard.answer)
    }
    var confidenceLevel by rememberSaveable {
        mutableStateOf(flashCard.confidenceLevel)
    }
    var isBookmarked by rememberSaveable {
        mutableStateOf(flashCard.isBookmarked)
    }
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(start = 20.dp, end = 20.dp, top = 30.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        OutlinedTextField(
            value = question,
            onValueChange = { question = it },
            shape = RoundedCornerShape(32.dp),
            placeholder = { Text(text = "Question") },
            minLines = 3,
            maxLines = 3,
            keyboardOptions = KeyboardOptions(
                autoCorrect = true,
                keyboardType = KeyboardType.Text
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp)
        )
        OutlinedTextField(
            value = answer,
            onValueChange = { answer = it },
            shape = RoundedCornerShape(32.dp),
            placeholder = { Text(text = "Answer") },
            minLines = 5,
            maxLines = 5,
            keyboardOptions = KeyboardOptions(
                autoCorrect = true,
                keyboardType = KeyboardType.Text
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp)
        )
        Text(
            text = "Confidence Level",
            style = Typography.labelLarge,
            modifier = Modifier.padding(top = 20.dp, bottom = 20.dp)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            for (i in 1..5) {
                ElevatedFilterChip(
                    selected = confidenceLevel == i,
                    onClick = { confidenceLevel = i },
                    label = {
                        Text(text = i.toString())
                    },
                    shape = RoundedCornerShape(16.dp)
                )
            }
        }

        Row(modifier = Modifier.fillMaxWidth().padding(bottom = 20.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(
                if(isBookmarked) Icons.Filled.BookmarkAdded else Icons.Filled.BookmarkAdd,
                contentDescription = "Bookmark Icon",
            )
            Text(text = "Bookmark", modifier = Modifier.padding(start = 10.dp))
            Spacer(modifier = Modifier.weight(1f))
            Switch(checked = isBookmarked, onCheckedChange = {isBookmarked = !isBookmarked})
        }
        Button(
            onClick = {
                viewModel.upsertFlashCard(flashCard.copy(question = question, answer = answer, confidenceLevel = confidenceLevel.toInt(), isBookmarked = isBookmarked))
                moveBackToHomeScreen()
            },
            enabled = question.isNotEmpty() && answer.isNotEmpty(),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Done")
        }
    }
//    }
}