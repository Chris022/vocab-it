package com.chris022.vocabit.features.flashcards

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
class FlashcardsViewModel @Inject constructor(
    private val flashCardRepository: FlashCardRepository,
    savedStateHandle: SavedStateHandle
): ViewModel(){

    //Info: MutableStateFlow is mutable (can be changed) while StateFlow is read only.
    //Since the State should only be changed inside the ViewModel the mutable version is private
    //But since the uiState sill needs to be readable by other objects the readonly version is public
    //Info: MutableStateFlow simply informs all subscribers about value changes
    private val _uiState = MutableStateFlow(FlashcardsUiState())
    val uiState: StateFlow<FlashcardsUiState> = _uiState.asStateFlow()

    private var n = 0
    private var maxIndex = 1

    private val setId: Int = savedStateHandle[DestinationsArgs.SET_INDEX_ARG]!!

    init {
        //first seed the Database
        seedDB()
        //load the number of flashcards
        loadCountFlashcards()
        //load the first Flashcard from the db
        loadFlashcard(n,setId)
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
        if(n < (maxIndex-1)){
            n++
            loadFlashcard(n, setId)
        }
    }

    fun prevCard(){
        if(n > 0){
            n--
            loadFlashcard(n, setId)
        }
    }

    private fun loadCountFlashcards(){
        //set loading true
        _uiState.update {
            it.copy(isLoading = true)
        }

        viewModelScope.launch {
            maxIndex = flashCardRepository.countFlashcards()
        }

    }

    private fun loadFlashcard(n: Int,setId: Int){
        //set loading true
        _uiState.update {
            it.copy(isLoading = true)
        }

        //this launches a async function. The viewModelScope automatically cancels the operation if
        //the view model is cleared
        viewModelScope.launch {
            flashCardRepository.getFlashcard(n,setId).let { flashCard ->
                if(flashCard != null){
                    _uiState.update {
                        it.copy(
                            setName = "HSK 1",
                            sideA = flashCard.sideA,
                            sideB = flashCard.sideB,
                            visibleSide = Side.A,
                            isLoading = false,
                            count = n+1
                        )
                    }
                }else{
                    _uiState.update {
                        it.copy( isLoading = false )
                    }
                }
            }

        }

    }

    private fun seedDB() = viewModelScope.launch {
        flashCardRepository.createFlashCard(1,"China","中国")
    }
}