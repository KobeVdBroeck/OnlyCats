package com.examenopdracht.onlycats.ui

import androidx.annotation.StringRes
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.examenopdracht.onlycats.R
import com.examenopdracht.onlycats.ui.components.CatNavBar
import com.examenopdracht.onlycats.ui.screens.FavouritesScreen
import com.examenopdracht.onlycats.ui.screens.HomeScreen
import com.examenopdracht.onlycats.ui.screens.SettingsScreen

enum class CatScreen(@StringRes val title: Int) {
    Home(title = R.string.home),
    Favourites(title = R.string.favourites),
    Settings(title = R.string.settings),
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OnlyCatsApp(
    navController: NavHostController = rememberNavController(),
    viewModel: HomeViewModel = viewModel(factory = HomeViewModel.Factory),
) {
    val backStackEntry by navController.currentBackStackEntryAsState()

    val currentScreen = CatScreen.valueOf(
        backStackEntry?.destination?.route ?: CatScreen.Home.name
    )

    Scaffold(
        bottomBar = {
            BottomAppBar {
                CatNavBar(currentScreen) { page -> GoToPage(navController, currentScreen, page) }
            }
        }
    ) { innerPadding ->

        // TODO remove this
        val koenk = innerPadding

        NavHost(
            navController = navController,
            startDestination = CatScreen.Home.name
        ) {
            composable(route = CatScreen.Home.name) {
                HomeScreen(viewModel.networkUiState)
            }

            composable(route = CatScreen.Favourites.name) {
                FavouritesScreen(viewModel.localUiState)
            }

            composable(route = CatScreen.Settings.name) {
                SettingsScreen()
            }
        }
    }

}

fun GoToPage(navController: NavHostController, currentScreen: CatScreen, screenName: String) {
    if(currentScreen.name == screenName)
        return

    navController.navigate(screenName)
}