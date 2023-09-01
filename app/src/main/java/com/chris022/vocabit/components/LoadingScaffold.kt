package com.chris022.vocabit.components

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun LoadingScaffold(
    isLoading:Boolean = false,
    content: @Composable () -> Unit
){
    if(isLoading){
        Text(text="Loading...")
        return
    }
    content()

}