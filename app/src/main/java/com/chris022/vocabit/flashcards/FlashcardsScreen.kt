package com.chris022.vocabit.flashcards

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
            Text(text = uiState.count.toString())
            Text(text = uiState.sideA)
            Text(text = uiState.sideB)
        }

    }
}