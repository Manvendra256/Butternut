package com.example.butternut.ui.bottomsheets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.butternut.ui.theme.Typography
import kotlinx.coroutines.CoroutineScope

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClassBottomSheet(
    sheetState: SheetState,
    scope: CoroutineScope,
    onDismissRequest: () -> Unit,
    editClass: () -> Unit,
    deleteClass: () -> Unit
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
                    .clickable {editClass()}) {
                    Icon(
                        Icons.Outlined.Edit,
                        contentDescription = "Edit",
                    )
                    Spacer(modifier = Modifier.width(20.dp))
                    Text(text = "Edit", fontSize = 20.sp)
                }
                Spacer(modifier = Modifier.height(20.dp))
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier
                    .clickable {deleteClass()}) {
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