package com.chris022.vocabit.features.editset

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditSetScreen (
    onBack: () -> Unit,
    viewModel: EditSetViewModel = hiltViewModel()
){

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    if (uiState.isLoading) {
        Text(text = "Loading...")
    } else {
        Column {
            uiState.flashcards.forEach {
                Card(
                    onClick = {
                        viewModel.toggleFlashcard(it.id)
                    }
                ) {
                    Text(text = it.sideA)
                    Text(text = it.sideB)
                    if (it.enabled) Text(text = "enabled")
                }
            }
        }
    }
}