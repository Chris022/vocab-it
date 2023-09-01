package com.chris022.vocabit.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun Loading(isLoading: Boolean){
    if(isLoading){
        Text(text="Loading...")
    }
}