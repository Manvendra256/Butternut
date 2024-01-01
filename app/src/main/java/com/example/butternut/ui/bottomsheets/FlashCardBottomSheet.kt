package com.example.butternut.ui.bottomsheets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.butternut.data.Class
import com.example.butternut.data.FlashCard
import com.example.butternut.navigation.Screen
import com.example.butternut.ui.theme.Typography
import com.example.butternut.ui.viewmodels.HomeViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlashCardBottomSheet(
    sheetState: SheetState,
    scope: CoroutineScope,
    goToUpdateFlashCardScreen: () -> Unit,
    deleteFlashCard: () -> Unit,
    onDismissRequest: () -> Unit,

) {
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
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
                Spacer(modifier = Modifier.height(20.dp))
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier
                    .padding(bottom = 10.dp)
                    .clickable { goToUpdateFlashCardScreen()}) {
                    Icon(
                        Icons.Outlined.Edit,
                        contentDescription = "Edit",
                    )
                    Spacer(modifier = Modifier.width(20.dp))
                    Text(text = "Edit", fontSize = 20.sp)
                }
                Spacer(modifier = Modifier.height(20.dp))
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier
                    .clickable {deleteFlashCard()}) {
                    Icon(
                        Icons.Outlined.Delete,
                        contentDescription = "Delete",
                    )
                    Spacer(modifier = Modifier.width(20.dp))
                    Text(text = "Delete", style = Typography.bodyLarge, fontSize = 20.sp)
                }
                Spacer(modifier = Modifier.height(30.dp))
            }
            Spacer(
                modifier = Modifier
                    .width(20.dp)
            )
        }
    }
}