package com.example.butternut.ui.bottomsheets

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.butternut.data.Deck
import com.example.butternut.ui.theme.Typography
import com.example.butternut.ui.viewmodels.ClassViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddOrUpdateDeckBottomSheet(
    viewModel: ClassViewModel,
    deck: Deck,
    update: Boolean,
    sheetState: SheetState,
    scope: CoroutineScope,
    onDismissRequest: () -> Unit
) {
//    Log.d("temp", deck.name)
    var text by rememberSaveable {
        mutableStateOf(deck.name)
    }
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = sheetState
    ) {
        Row() {
            Spacer(
                modifier = Modifier
                    .width(20.dp)
            )
            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                Text(
                    text = if (update) "Update Deck" else "Add Deck",
                    style = Typography.titleLarge,
                    modifier = Modifier.padding(bottom = 10.dp)
                )
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(32.dp),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(onDone = {
                        scope.launch {
                            if (update) {
                                viewModel.updateDeck(deck.copy(name = text))
                            } else {
                                viewModel.insertDeck(Deck(name = text, classId = deck.classId))
                            }
                        }
                        onDismissRequest()
                    }),
                    singleLine = true,
                    value = text,
                    onValueChange = {
                        text = it
                    },
                    trailingIcon = {
                        IconButton(onClick = { text = "" }) {
                            Icon(
                                Icons.Filled.Close,
                                contentDescription = "Start Again",
                            )
                        }
                    },
                    placeholder = { Text(text = "Name") })
                Button(onClick = {
                    scope.launch {
                        if (update) {
                            viewModel.updateDeck(deck.copy(name = text))
                        } else {
                            viewModel.insertDeck(Deck(name = text, classId = deck.classId))
                        }
                    }
                    onDismissRequest()
                }, enabled = text.isNotEmpty(), modifier = Modifier.fillMaxWidth()) {
                    Text(text = "Done")
                }
            }
            Spacer(
                modifier = Modifier
                    .width(20.dp)
            )
        }
    }
}