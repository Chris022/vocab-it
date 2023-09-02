package com.chris022.vocabit.features.sets

import android.annotation.SuppressLint
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.chris022.vocabit.components.LoadingScaffold
import com.chris022.vocabit.components.Navbar
import com.chris022.vocabit.components.NavbarScaffold
import com.chris022.vocabit.components.useTextInputDialog
import com.chris022.vocabit.features.sets.components.Filters
import com.chris022.vocabit.features.sets.components.TopBar

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SetsScreen(
    onHome: () -> Unit,
    onLoadSet: (Int) -> Unit,
    onEditSet: (Int) -> Unit,
    viewModel: SetsViewModel = hiltViewModel()
) {
    //the Ui State
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    //can be used to open the name Dialog for when importing a new Set
    val (dialogComponent, openSetNameInputDialog) = useTextInputDialog("Enter a name for your Set:")

    //for the bottom sheet
    var openBottomSheet by rememberSaveable { mutableStateOf(false) }

    //for opening a file
    val pickFileLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.OpenDocument()) {
            if (it != null) {
                openSetNameInputDialog { name ->
                    viewModel.importCSV(it, name)
                }
            }
        }

    Scaffold(
        topBar = { TopBar("Sets", "Pick a set to practice") },
        bottomBar = {
            Navbar(onHome = onHome, onSets = { }, selected = 1)
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    pickFileLauncher.launch(
                        arrayOf(
                            "text/csv",
                            "text/comma-separated-values",
                            "application/csv"
                        )
                    )
                },
            ) {
                Icon(Icons.Filled.Add, "New Set")
            }
        },
    ) { padding ->
        LoadingScaffold(
            isLoading = uiState.isLoading,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(padding)
                    .padding(16.dp, 4.dp, 16.dp, 0.dp)
            ) {
                Filters()
                Column(
                    modifier = Modifier
                        .padding(16.dp, 4.dp, 16.dp, 0.dp)
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally
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
        }
        BottomSheet(
            openBottomSheet,
            close = { openBottomSheet = false },
            onLoadSet = { onLoadSet(uiState.selectedSet) },
            onEditSet = { onEditSet(uiState.selectedSet) },
            onDeleteSet = { viewModel.deleteSet(uiState.selectedSet) }
        )
    }

    dialogComponent()
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheet(
    isOpen: Boolean,
    close: () -> Unit,
    onLoadSet: () -> Unit,
    onEditSet: () -> Unit,
    onDeleteSet: () -> Unit
) {
    val bottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    if (isOpen) {
        ModalBottomSheet(
            onDismissRequest = { close() },
            sheetState = bottomSheetState,
            windowInsets = BottomSheetDefaults.windowInsets
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                ActionListItem(
                    text = "Review",
                    icon = Icons.Default.PlayArrow,
                    onClick = { onLoadSet() }
                )
                ActionListItem(
                    text = "Edit",
                    icon = Icons.Default.Settings,
                    onClick = { onEditSet() }
                )
                ActionListItem(
                    text = "Delete",
                    icon = Icons.Default.Delete,
                    onClick = { onDeleteSet(); close() }
                )
                Spacer(modifier = Modifier.size(38.dp))
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActionListItem(
    text: String,
    icon: ImageVector,
    onClick: () -> Unit
) {
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
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                icon,
                contentDescription = text,
                modifier = Modifier
                    .padding(24.dp, 8.dp, 8.dp, 8.dp)
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
            .height(190.dp)
            .width(270.dp)
            .padding(20.dp,15.dp,20.dp,15.dp),
        onClick = onClick,
        shape = RoundedCornerShape(25.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(32.dp)
        ) {
            Text(text = name, style = MaterialTheme.typography.titleMedium)
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp,32.dp,0.dp,0.dp),
                horizontalArrangement = Arrangement.Center
            ){
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .padding(8.dp,4.dp,8.dp,4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "$count phrases"
                        )
                        Spacer(Modifier.size(20.dp))
                        Icon(
                            imageVector = Icons.Filled.PlayArrow,
                            contentDescription = "Play symbol",
                            modifier = Modifier.size(FilterChipDefaults.IconSize),
                        )

                    }
                }
            }
        }

    }
}