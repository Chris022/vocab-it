package com.chris022.vocabit.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun NavbarScaffold(
    onHome: () -> Unit,
    onSets: () -> Unit,
    selected: Int,
    content: @Composable BoxScope.() -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {

        content()

        NavigationBar(
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            NavigationBarItem(
                icon = { Icon(Icons.Filled.Home, contentDescription = "Home") },
                label = { Text("Home") },
                selected = selected == 0,
                onClick = { onHome() }
            )
            NavigationBarItem(
                icon = { Icon(Icons.Filled.List, contentDescription = "Sets") },
                label = { Text("Sets") },
                selected = selected == 1,
                onClick = { onSets() }
            )
        }
    }

}

@Composable
fun Navbar(
    onHome: () -> Unit,
    onSets: () -> Unit,
    selected: Int,
) {
    NavigationBar() {
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Home, contentDescription = "Home") },
            label = { Text("Home") },
            selected = selected == 0,
            onClick = { onHome() }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Filled.List, contentDescription = "Sets") },
            label = { Text("Sets") },
            selected = selected == 1,
            onClick = { onSets() }
        )
    }
}