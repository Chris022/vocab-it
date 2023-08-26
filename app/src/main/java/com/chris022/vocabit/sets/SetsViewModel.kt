package com.chris022.vocabit.sets

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chris022.vocabit.data.SetRepository
import com.chris022.vocabit.flashcards.FlashcardsUiState
import com.chris022.vocabit.flashcards.Side
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SetsViewModel @Inject constructor(
    private val setRepository: SetRepository
): ViewModel(){

    //Info: MutableStateFlow is mutable (can be changed) while StateFlow is read only.
    //Since the State should only be changed inside the ViewModel the mutable version is private
    //But since the uiState sill needs to be readable by other objects the readonly version is public
    //Info: MutableStateFlow simply informs all subscribers about value changes
    private val _uiState = MutableStateFlow(SetsUiState())
    val uiState: StateFlow<SetsUiState> = _uiState.asStateFlow()


    init {
        //first create a new set
        seedDB()
        //load all sets
        loadSets(_uiState.value.selectedSetType)
    }

    fun changeCategory(type: SetType){
        _uiState.update {
            it.copy(
                selectedSetType = type,
            )
        }
        loadSets(type)
    }

    private fun loadSets(type: SetType){
        //set loading true
        _uiState.update {
            it.copy(isLoading = true)
        }

        //this launches a async function. The viewModelScope automatically cancels the operation if
        //the view model is cleared
        viewModelScope.launch {
            setRepository.getAllWithFlashcards(type).let { sets ->
                _uiState.update {
                    it.copy(
                        sets = sets.map { SetUiState(it.set.name,it.flashcards.size) },
                        isLoading = false
                    )
                }
            }
        }
    }


    private fun seedDB() = viewModelScope.launch {
        setRepository.createSet("HSK 1", SetType.Reading)
    }

}