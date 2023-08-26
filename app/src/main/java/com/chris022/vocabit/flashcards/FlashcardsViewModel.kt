package com.chris022.vocabit.flashcards

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class FlashcardsViewModel @Inject constructor(): ViewModel(){

    //Info: MutableStateFlow is mutable (can be changed) while StateFlow is read only.
    //Since the State should only be changed inside the ViewModel the mutable version is private
    //But since the uiState sill needs to be readable by other objects the readonly version is public
    //Info: MutableStateFlow simply informs all subscribers about value changes
    private val _uiState = MutableStateFlow(FlashcardsUiState())
    val uiState: StateFlow<FlashcardsUiState> = _uiState.asStateFlow()

    private val index = 0

    init {
        //load the first Flashcard from the db
        loadFlashcard(index)
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


    private fun loadFlashcard(index: Int){
        //set loading true
        _uiState.update {
            it.copy(isLoading = true)
        }

        //this launches a async function. The viewModelScope automatically cancels the operation if
        //the view model is cleared
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                Thread.sleep(5000)
            };
            //TODO get word from database
            _uiState.update {
                it.copy(
                    setName = "HSK 1",
                    sideA = "China",
                    sideB = "中国",
                    visibleSide = Side.A,
                    isLoading = false,
                    count = index + 1
                )
            }
        }

    }



}