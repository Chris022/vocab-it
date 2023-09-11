package com.chris022.vocabit.components


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.AsyncUpdates
import com.airbnb.lottie.LottieComposition
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieConstants

@Composable
fun LoadingScaffold(
    isLoading: Boolean = false,
    composition: LottieComposition?,
    content: @Composable () -> Unit
) {
    if (isLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier.align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LottieAnimation(
                    modifier = Modifier
                        .size(150.dp),
                    asyncUpdates = AsyncUpdates.ENABLED,
                    composition = composition,
                    isPlaying = true,
                    iterations = LottieConstants.IterateForever,
                    reverseOnRepeat = true
                )
                Text(text = "Loading...", style = MaterialTheme.typography.titleSmall)
            }
        }
    }else {
        content()
    }



}