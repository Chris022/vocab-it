package com.chris022.vocabit

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.compose.runtime.remember
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.chris022.vocabit.DestinationsArgs.SET_INDEX_ARG
import com.chris022.vocabit.features.editset.EditSetScreen
import com.chris022.vocabit.features.flashcards.FlashcardsScreen
import com.chris022.vocabit.features.home.HomeScreen
import com.chris022.vocabit.features.sets.SetsScreen

@Composable
fun NavGraph(
    navController: NavHostController = rememberNavController(),
    startDestination: String = Destinations.HOME_ROUTE,
    navActions: NavigationActions = remember(navController) {
        NavigationActions(navController)
    }
) {
    val loadingComposition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.loading_animation))
    NavHost(
        navController = navController,
        startDestination = startDestination,
    ) {
        composable(
            Destinations.HOME_ROUTE,
            arguments = listOf()
        ) { entry ->
            HomeScreen(
                onOpenSets = { navActions.navigateToSets() }
            )
        }
        composable(
            Destinations.FLASHCARDS_ROUTE,
            arguments = listOf(
                navArgument(SET_INDEX_ARG) { type = NavType.IntType; defaultValue = 0 }
            )
        ) { entry ->
            FlashcardsScreen(
                onBack = { navActions.navigateToSets() },
                loadingComposition = loadingComposition
            )
        }
        composable(
            Destinations.SETS_ROUTE,
            arguments = listOf()
        ) { entry ->
            SetsScreen(
                onHome = {
                    navActions.navigateToHome()
                },
                onLoadSet = { navActions.navigateToFlashcards(it) },
                onEditSet = { navActions.navigateToEditSet(it) },
                loadingComposition = loadingComposition
            )
        }
        composable(
            Destinations.EDIT_SET_ROUTE,
            arguments = listOf(
                navArgument(SET_INDEX_ARG) { type = NavType.IntType; defaultValue = 0 }
            )
        ) { entry ->
            EditSetScreen(
                onBack = { navActions.navigateToSets() },
                loadingComposition = loadingComposition
            )
        }
    }
}