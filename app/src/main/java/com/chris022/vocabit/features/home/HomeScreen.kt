package com.chris022.vocabit.features.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle


@Composable
fun HomeScreen(
    onOpenSets: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Box(modifier = Modifier.fillMaxSize()) {

        Text(modifier = Modifier.align(Alignment.Center),text = "Aligned to start center ")

        NavigationBar(
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            NavigationBarItem(
                icon = { Icon(Icons.Filled.Home, contentDescription = "Home") },
                label = { Text("Home") },
                selected = true,
                onClick = {  }
            )
            NavigationBarItem(
                icon = { Icon(Icons.Filled.List, contentDescription = "Sets") },
                label = { Text("Sets") },
                selected = false,
                onClick = { onOpenSets() }
            )
        }
    }



}
