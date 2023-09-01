package com.chris022.vocabit.features.editset

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chris022.vocabit.DestinationsArgs
import com.chris022.vocabit.data.FlashCardRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditSetViewModel @Inject constructor(
    private val flashCardRepository: FlashCardRepository,
    savedStateHandle: SavedStateHandle
): ViewModel(){

    //Info: MutableStateFlow is mutable (can be changed) while StateFlow is read only.
    //Since the State should only be changed inside the ViewModel the mutable version is private
    //But since the uiState sill needs to be readable by other objects the readonly version is public
    //Info: MutableStateFlow simply informs all subscribers about value changes
    private val _uiState = MutableStateFlow(EditSetUiState())
    val uiState: StateFlow<EditSetUiState> = _uiState.asStateFlow()

    private val setId: Int = savedStateHandle[DestinationsArgs.SET_INDEX_ARG]!!

    init {
        loadFlashcards(setId)
    }

    fun toggleFlashcard(flashcardId: Int){
        viewModelScope.launch {
            val flashcard = flashCardRepository.toggleFlashcardEnabled(flashcardId)?.let { FlashcardUiState(it.sideA, it.sideB, it.enabled, it.id) }
            if(flashcard != null){
                _uiState.update { outerit ->
                    outerit.copy(
                        flashcards = outerit.flashcards.map {
                            if (it.id == flashcardId) flashcard else it
                        }
                    )
                }
            }
        }
    }

    private fun loadFlashcards(setId: Int){
        //set loading true
        _uiState.update {
            it.copy(isLoading = true)
        }

        //this launches a async function. The viewModelScope automatically cancels the operation if
        //the view model is cleared
        viewModelScope.launch {
            flashCardRepository.getAllFlashcards(setId).let { flashcards ->
                _uiState.update {
                    it.copy(
                        flashcards = flashcards.map { FlashcardUiState(it.sideA, it.sideB, it.enabled, it.id) },
                        isLoading = false
                    )
                }
            }
        }
    }
}