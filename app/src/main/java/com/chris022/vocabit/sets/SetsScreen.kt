package com.chris022.vocabit.sets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Surface
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.chris022.vocabit.flashcards.FlashCard

@Composable
fun SetsScreen(
    onHome: () -> Unit,
    viewModel: SetsViewModel = hiltViewModel()
){
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    if (uiState.isLoading) {
        Text(text = "Loading...")
    } else {
        Column {
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
            }
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) { uiState.sets.forEach { SetCard(name = it.name, count = it.count) }
            }
        }
    }
}

@Composable
fun SetCard(
    name: String,
    count: Int
){
    Card (
        modifier = Modifier
            .height(140.dp)
            .width(240.dp)
            .padding(20.dp)
    ){
        Column {
            Text(text = name)
            Text(text = "$count phrases")
        }
    }
}