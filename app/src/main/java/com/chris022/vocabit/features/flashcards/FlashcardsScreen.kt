package com.chris022.vocabit.features.flashcards

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
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


@Composable
fun FlashcardsScreen (
    onBack: () -> Unit,
    viewModel: FlashcardsViewModel = hiltViewModel()
){
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    if (uiState.isLoading) {
        Text(text = "Loading...")
    } else {
        Column {
            Button(onClick = onBack) {
                Text(text = "<-")
            }
            Text(text = uiState.setName)
            Row {
                Button(onClick = { viewModel.prevCard() }) {
                    Text(text = "<<")
                }
                Text(text = uiState.count.toString())
                Button(onClick = { viewModel.nextCard() }) {
                    Text(text = ">>")
                }
            }

            FlashCard(sideA = uiState.sideA, sideB = uiState.sideB, side = uiState.visibleSide, flipSide = viewModel::flipCard)
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
            .height(100.dp)
            .width(100.dp)
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