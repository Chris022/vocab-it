package com.chris022.vocabit

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.compose.runtime.remember
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.chris022.vocabit.DestinationsArgs.SET_INDEX_ARG
import com.chris022.vocabit.flashcards.FlashcardsScreen
import com.chris022.vocabit.sets.SetsScreen

@Composable
fun NavGraph(
    navController: NavHostController = rememberNavController(),
    startDestination: String = Destinations.SETS_ROUTE,
    navActions: NavigationActions = remember(navController) {
        NavigationActions(navController)
    }
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
    ) {
        composable(
            Destinations.FLASHCARDS_ROUTE,
            arguments = listOf(
                navArgument(SET_INDEX_ARG) { type = NavType.IntType; defaultValue = 0 }
            )
        ) { entry ->
            FlashcardsScreen(
                onBack = { /*TODO*/ }
            )
        }
        composable(
            Destinations.SETS_ROUTE,
            arguments = listOf()
        ) { entry ->
            SetsScreen(
                onHome = { /*TODO*/ }
            )
        }
    }
}