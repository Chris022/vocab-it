package com.chris022.vocabit.flashcards

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle


@Composable
fun FlashcardsScreen (
    onBack: () -> Unit,
    viewModel: FlashcardsViewModel = hiltViewModel()
){
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    if (uiState.isLoading) {
        Text(text = "Loading...")
    } else {
        Column {
            Text(text = uiState.setName)
            Row {
                Button(onClick = { viewModel.prevCard() }) {
                    Text(text = "<<")
                }
                Text(text = uiState.count.toString())
                Button(onClick = { viewModel.nextCard() }) {
                    Text(text = ">>")
                }
            }

            FlashCard(sideA = uiState.sideA, sideB = uiState.sideB, side = uiState.visibleSide, flipSide = viewModel::flipCard)
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlashCard(
    sideA: String,
    sideB: String,
    side: Side,
    flipSide: () -> Unit
){
    Card (
        onClick = flipSide,
        modifier = Modifier
            .height(100.dp)
            .width(100.dp)
    ){
        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ){
            if(side == Side.A){
                Text(sideA)
            }else{
                Text(sideB)
            }
        }
    }
}