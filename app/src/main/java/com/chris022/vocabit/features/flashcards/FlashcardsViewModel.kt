package com.chris022.vocabit.features.flashcards

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chris022.vocabit.DestinationsArgs
import com.chris022.vocabit.data.FlashCardRepository
import com.chris022.vocabit.data.SetRepository
import com.chris022.vocabit.data.source.FlashCard
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FlashcardsViewModel @Inject constructor(
    private val flashCardRepository: FlashCardRepository,
    private val setRepository: SetRepository,
    savedStateHandle: SavedStateHandle
): ViewModel(){

    //Info: MutableStateFlow is mutable (can be changed) while StateFlow is read only.
    //Since the State should only be changed inside the ViewModel the mutable version is private
    //But since the uiState sill needs to be readable by other objects the readonly version is public
    //Info: MutableStateFlow simply informs all subscribers about value changes
    private val _uiState = MutableStateFlow(FlashcardsUiState())
    val uiState: StateFlow<FlashcardsUiState> = _uiState.asStateFlow()

    //a list of all enabled Flashcards for the selected set - only modifiable by the view-model
    private var _flashcardStack = listOf<FlashCard> ()

    private var n = 0

    private val setId: Int = savedStateHandle[DestinationsArgs.SET_INDEX_ARG]!!

    init {
        loadSet(setId)

        loadFlashcards(setId)
    }

    fun flipCard(){
        if(_uiState.value.visibleSide == Side.A){
            _uiState.update {
                it.copy(
                    visibleSide = Side.B
                )
            }
        }else{
            _uiState.update {
                it.copy(
                    visibleSide = Side.A
                )
            }
        }
    }

    fun nextCard(){
        if(n < (_flashcardStack.size-1)){
            n++
            updateDisplayedFlashcard(n)
        }
    }

    fun prevCard(){
        if(n > 0){
            n--
            updateDisplayedFlashcard(n)
        }
    }

    private fun loadSet(setId: Int){
        _uiState.update {
            it.copy(isLoading = true)
        }

        viewModelScope.launch {
            setRepository.find(setId).let {set ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        setName = set.name
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
            flashCardRepository.getEnabledFlashcardsForSet(setId).let { flashcards ->
                _flashcardStack = flashcards
                n = 0
                updateDisplayedFlashcard(0)
            }

            _uiState.update {
                it.copy(isLoading = false)
            }
        }
    }

    private fun updateDisplayedFlashcard(n: Int){
        if(_flashcardStack.elementAtOrNull(n) == null) return
        _uiState.update {
            it.copy(
                sideA = _flashcardStack[n].sideA,
                sideB = _flashcardStack[n].sideB,
                visibleSide = Side.A,
                isLoading = false,
                count = _flashcardStack.size,
                n = n
            )
        }
    }

}