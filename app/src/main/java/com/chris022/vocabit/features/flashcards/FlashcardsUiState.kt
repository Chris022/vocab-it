package com.chris022.vocabit.features.flashcards

data class FlashcardsUiState(
    val setName: String = "",
    val sideA: String = "",
    val sideB: String = "",
    val visibleSide: Side = Side.A,
    val count: Int = 1,
    val n: Int = 1,
    val isLoading: Boolean = false
)

enum class Side {
    A, B
}