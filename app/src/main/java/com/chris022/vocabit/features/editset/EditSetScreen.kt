package com.chris022.vocabit.features.editset

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.chris022.vocabit.components.LoadingScaffold
import com.chris022.vocabit.features.sets.BottomSheet
import com.chris022.vocabit.features.sets.SetCard
import com.chris022.vocabit.features.sets.components.Filters
import com.chris022.vocabit.features.sets.components.TopBar


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditSetScreen (
    onBack: () -> Unit,
    viewModel: EditSetViewModel = hiltViewModel()
){
    //the Ui State
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold (
        topBar = { TopBar("Edit Set", "Enable/Disable Flashcards") },
    ){ padding ->
        LoadingScaffold(
            isLoading = uiState.isLoading,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(padding)
                    .padding(16.dp, 4.dp, 16.dp, 0.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                uiState.flashcards.forEachIndexed { index, it ->
                    FlashcardItem((index + 1).toString(),it.sideA,it.sideB,it.enabled) {
                        viewModel.toggleFlashcard(
                            it.id
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun FlashcardItem(
    number: String,
    sideA: String,
    sideB: String,
    enabled: Boolean,
    onChange: () -> Unit
){
    Row (
        modifier = Modifier
            .padding(10.dp,0.dp,10.dp,0.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Row (
            verticalAlignment = Alignment.CenterVertically,
        ){
            Text(text = number,style= MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.width(10.dp))
            Column {
                Text(text = "Side A: $sideA",style= MaterialTheme.typography.bodyMedium)
                Text(text = "Side B: $sideB",style= MaterialTheme.typography.bodyMedium)
            }
        }
        Checkbox(checked = enabled, onCheckedChange = {_->onChange()})
    }
    HorizontalDivider()
}