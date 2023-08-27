package com.chris022.vocabit

import androidx.navigation.NavHostController
import com.chris022.vocabit.DestinationsArgs.SET_INDEX_ARG
import com.chris022.vocabit.Screens.FLASHCARDS_SCREEN
import com.chris022.vocabit.Screens.HOME_SCREEN
import com.chris022.vocabit.Screens.SETS_SCREEN


private object Screens {
    const val FLASHCARDS_SCREEN = "flashcards"
    const val SETS_SCREEN = "sets"
    const val HOME_SCREEN = "home"
}


object DestinationsArgs {
    const val SET_INDEX_ARG = "set_index"
}


object Destinations {
    const val FLASHCARDS_ROUTE = "$FLASHCARDS_SCREEN?$SET_INDEX_ARG={$SET_INDEX_ARG}"
    const val SETS_ROUTE = SETS_SCREEN
    const val HOME_ROUTE = HOME_SCREEN
}


class NavigationActions(private val navController: NavHostController) {

    fun navigateToFlashcards(setIndex: Int) {
        navController.navigate(
            FLASHCARDS_SCREEN.let {
                "$it?$SET_INDEX_ARG=$setIndex"
            }
        ) {}
    }

    fun navigateToSets() {
        navController.navigate(
            SETS_SCREEN
        )
    }

    fun navigateToHome() {
        navController.navigate(
            HOME_SCREEN
        )
    }
}