package com.chris022.vocabit.editset

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.chris022.vocabit.data.FlashCardRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
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


    init {

    }
}