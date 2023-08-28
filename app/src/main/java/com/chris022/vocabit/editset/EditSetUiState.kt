package com.chris022.vocabit.editset

import kotlinx.coroutines.flow.StateFlow

data class FlashcardUiState (
    val sideA: String,
    val sideB: String,
    val enabled: Boolean,
    val id: Int
)

data class EditSetUiState (
    val flashcards: List<FlashcardUiState> = listOf(),
    val isLoading: Boolean = false
)
