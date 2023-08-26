package com.chris022.vocabit.sets

data class SetUiState(
    val name: String = "",
    val count: Int = 0
)

data class SetsUiState(
    val selectedSetType: SetType = SetType.Reading,
    val sets: List<SetUiState> = listOf(),
    val isLoading: Boolean = false
)

enum class SetType {
    Reading,
    Writing
}