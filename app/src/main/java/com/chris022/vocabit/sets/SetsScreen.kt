package com.chris022.vocabit.sets

import android.icu.number.NumberFormatter.UnitWidth
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.chris022.vocabit.components.TextInputDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SetsScreen(
    onHome: () -> Unit,
    onLoadSet: (Int) -> Unit,
    onEditSet: (Int) -> Unit,
    viewModel: SetsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    if (uiState.isLoading) {
        Text(text = "Loading...")
    } else {

        var openSetNameInputDialog by remember { mutableStateOf(false) }
        var selectedUri by remember { mutableStateOf<Uri?>(null) }
        var openBottomSheet by rememberSaveable { mutableStateOf(false) }
        val bottomSheetState = rememberModalBottomSheetState(
            skipPartiallyExpanded = true
        )
        val pickFileLauncher = rememberLauncherForActivityResult(ActivityResultContracts.OpenDocument()){
            selectedUri = it
            openSetNameInputDialog = true
        }

        Column (
            modifier = Modifier.fillMaxWidth()
        ){
            Row {
                Button(onClick = { viewModel.changeCategory(SetType.Reading) }) {
                    Text("Reading")
                }
                Button(onClick = { viewModel.changeCategory(SetType.Writing) }) {
                    Text("Writing")
                }
                Button(onClick = onHome) {
                    Text("Home")
                }
                Button( onClick = {
                    pickFileLauncher.launch(arrayOf(
                            "text/csv",
                            "text/comma-separated-values",
                            "application/csv"
                        ))
                }){
                    Text(text = "open csv")
                }
            }
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                uiState.sets.forEach {
                    SetCard(
                        name = it.name,
                        count = it.count,
                        onClick = {
                            openBottomSheet = !openBottomSheet
                            viewModel.selectSet(it.id)
                        })
                }
            }
        }
        if(openBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = { openBottomSheet = false },
                sheetState = bottomSheetState,
                windowInsets = BottomSheetDefaults.windowInsets
            ) {
                Column (
                    modifier = Modifier
                        .fillMaxWidth()
                ){
                    ActionListItem(
                        text = "Review",
                        icon = Icons.Default.PlayArrow,
                        onClick = { onLoadSet(uiState.selectedSet) }
                    )
                    ActionListItem(
                        text = "Edit",
                        icon = Icons.Default.Settings,
                        onClick = { onEditSet(uiState.selectedSet) }
                    )
                    ActionListItem(
                        text = "Delete",
                        icon = Icons.Default.Delete,
                        onClick = { viewModel.deleteSet(uiState.selectedSet); openBottomSheet = false }
                    )
                    Spacer(modifier = Modifier.size(38.dp))
                }
            }

        }

        TextInputDialog(
            isOpen = openSetNameInputDialog,
            heading = "Enter a name for your Set:",
            onConfirm = {
                if(selectedUri != null){
                    viewModel.importCSV(selectedUri as Uri,it)
                }
                openSetNameInputDialog = false
            }
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActionListItem(
    text: String,
    icon: ImageVector,
    onClick: () -> Unit
){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        ),
        shape = RectangleShape,
        onClick = onClick
    ) {
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            verticalAlignment = Alignment.CenterVertically
        ){
            Icon(
                icon,
                contentDescription = text,
                modifier = Modifier
                    .padding(24.dp,8.dp,8.dp,8.dp)
            )
            Text(
                text = text,
                fontSize = 16.sp,
                modifier = Modifier
                    .padding(8.dp)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SetCard(
    name: String,
    count: Int,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .height(140.dp)
            .width(240.dp)
            .padding(20.dp),
        onClick = onClick
    ) {
        Column {
            Text(text = name)
            Text(text = "$count phrases")
        }
    }
}