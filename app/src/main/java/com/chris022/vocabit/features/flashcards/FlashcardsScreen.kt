package com.chris022.vocabit.features.flashcards

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.airbnb.lottie.LottieComposition
import com.chris022.vocabit.components.LoadingScaffold
import com.chris022.vocabit.components.Navbar
import com.chris022.vocabit.features.sets.BottomSheet
import com.chris022.vocabit.features.sets.SetCard
import com.chris022.vocabit.features.sets.components.Filters
import com.chris022.vocabit.features.sets.components.TopBar


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlashcardsScreen (
    onBack: () -> Unit,
    viewModel: FlashcardsViewModel = hiltViewModel(),
    loadingComposition: LottieComposition?
){
    //the Ui State
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = { TopBar("Set: " + uiState.setName, "Click to flip a card") },
    ) { padding ->
        LoadingScaffold(
            isLoading = uiState.isLoading,
            composition = loadingComposition
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp, 4.dp, 16.dp, 16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = (uiState.n+1).toString() + " / " + uiState.count.toString(), style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.height(30.dp))
                Row (
                    verticalAlignment = Alignment.CenterVertically
                ){
                    IconButton(onClick = { viewModel.prevCard() }) {
                        Icon(
                            imageVector = Icons.Filled.KeyboardArrowLeft,
                            contentDescription = "Selected",
                            modifier = Modifier.size(FilterChipDefaults.IconSize)
                        )
                    }
                    FlashCard(sideA = uiState.sideA, sideB = uiState.sideB, side = uiState.visibleSide, flipSide = viewModel::flipCard)
                    IconButton(onClick = { viewModel.nextCard() }) {
                        Icon(
                            imageVector = Icons.Filled.KeyboardArrowRight,
                            contentDescription = "Selected",
                            modifier = Modifier.size(FilterChipDefaults.IconSize)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(30.dp))
                Button(onClick = viewModel::flipCard) {
                   Text(text = "Flip")
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlashCard(
    sideA: String,
    sideB: String,
    side: Side,
    flipSide: () -> Unit
){
    val rotation: Float by animateFloatAsState(
        targetValue = if(side == Side.A) 0f else 180f,
        animationSpec = tween(durationMillis = 500, easing = LinearEasing),
        label = "Flip Card Animation"
    )
    Card (
        onClick = flipSide,
        modifier = Modifier
            .graphicsLayer {
                rotationY = rotation
                cameraDistance = 8 * density
            }
            .height(200.dp)
            .width(200.dp)
    ){
        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ){
            if(rotation < 90f){
                Text(sideA)
            }else{
                Text(
                    modifier = Modifier.graphicsLayer {
                        rotationY = 180f
                    },
                    text = sideB
                )
            }
        }
    }
}

@Composable
@Preview
fun FlashCardPreview() {
    FlashCard(sideA = "Side A", sideB = "Side B", side = Side.A) {}
}