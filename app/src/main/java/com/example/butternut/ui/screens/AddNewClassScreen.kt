package com.example.butternut.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import com.example.butternut.data.Class
import com.example.butternut.ui.viewmodels.HomeViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNewClassScreen(viewModel: HomeViewModel, moveBackToHomeScreen: () -> Unit) {
    val coroutineScope = rememberCoroutineScope()
    var text by rememberSaveable {
        mutableStateOf("")
    }
    Scaffold(topBar = { CenterAlignedTopAppBar(title = { Text("Butternut") }) }) {
        Column(modifier = Modifier.padding(it)) {
            OutlinedTextField(value = text, onValueChange = { text = it })
            OutlinedButton(onClick = {
                coroutineScope.launch {
                    viewModel.insertClass(Class(name = text))
                }
                moveBackToHomeScreen()
            }, enabled = text.isNotEmpty()) {
                Text(text = "Add Class")
            }
        }
    }
}