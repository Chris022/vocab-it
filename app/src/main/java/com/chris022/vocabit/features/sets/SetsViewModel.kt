package com.chris022.vocabit.features.sets

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chris022.vocabit.data.FlashCardRepository
import com.chris022.vocabit.data.SetRepository
import com.chris022.vocabit.data.source.FlashCard
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.InputStreamReader
import javax.inject.Inject

@HiltViewModel
class SetsViewModel @Inject constructor(
    private val setRepository: SetRepository,
    private val flashCardRepository: FlashCardRepository,
    @ApplicationContext applicationContext: Context
): ViewModel(){

    //Info: MutableStateFlow is mutable (can be changed) while StateFlow is read only.
    //Since the State should only be changed inside the ViewModel the mutable version is private
    //But since the uiState sill needs to be readable by other objects the readonly version is public
    //Info: MutableStateFlow simply informs all subscribers about value changes
    private val _uiState = MutableStateFlow(SetsUiState())
    val uiState: StateFlow<SetsUiState> = _uiState.asStateFlow()

    val contentResolver = applicationContext.contentResolver


    init {
        //load all sets
        loadSets(_uiState.value.selectedSetType)
    }

    fun deleteSet(id: Int){
        viewModelScope.launch {
            setRepository.delete(id);
        }

        //reload sets
        loadSets(_uiState.value.selectedSetType)
    }

    fun importCSV(uri: Uri, name: String){
        //interpret csv
        var flashcards = listOf<FlashCard>()
        contentResolver.openInputStream(uri)?.use { inputStream ->
            BufferedReader(InputStreamReader(inputStream)).use { reader ->
                val header = reader.readLine()
                flashcards = reader.lineSequence()
                    .filter { it.isNotBlank() }
                    .map {
                        val (number, sideA, sideB) = it.split(';', ignoreCase = false, limit = 3)
                        FlashCard(0,sideA, sideB, 0, true)
                    }.toList()
            }
        }
        //create new Set
        viewModelScope.launch {
            val id = setRepository.createSet(name, SetType.ReadWrite)

            //next create all flashcards in db
            flashcards.forEach{
                flashCardRepository.createFlashCard(id.toInt(),it.sideA,it.sideB)
            }

            //reload sets
            loadSets(_uiState.value.selectedSetType)
        }
    }

    fun selectSet(setId: Int){

        _uiState.update {
            it.copy(
                selectedSet = setId
            )
        }
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
            setRepository.getAll(type).let { sets ->
                _uiState.update {
                    it.copy(
                        sets = sets.map {
                                            //count number of flashcards
                                            val size = setRepository.countFlashcardsInSet(it.id)
                                            SetUiState(it.name,size,it.id)
                                        }
                    )
                }
            }
            _uiState.update { it.copy(isLoading = false) }
        }
    }
}