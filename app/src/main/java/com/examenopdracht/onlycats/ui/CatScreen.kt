package com.examenopdracht.onlycats.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.examenopdracht.onlycats.R
import com.examenopdracht.onlycats.model.HomeViewModel
import com.examenopdracht.onlycats.ui.components.NavComponent
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
    windowSizeClass: WindowSizeClass
) {
    val backStackEntry by navController.currentBackStackEntryAsState()

    val currentScreen = CatScreen.valueOf(
        backStackEntry?.destination?.route ?: CatScreen.Home.name
    )


    Scaffold{ innerPadding ->

        // TODO remove this
        val koenk = innerPadding
        val networkUiState by viewModel.networkUiState.collectAsState()
        val localUiState by viewModel.localUiState.collectAsState()

        NavComponent(currentScreen, { page -> GoToPage(navController, currentScreen, page) }, windowSizeClass)

        NavHost(
            navController = navController,
            startDestination = CatScreen.Home.name,
            modifier = Modifier.padding(start = if (windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact) 0.dp else 85.dp,
                                        bottom = if (windowSizeClass.widthSizeClass != WindowWidthSizeClass.Compact) 0.dp else 85.dp)) {
            composable(route = CatScreen.Home.name) {
                HomeScreen(networkUiState, windowSizeClass)
            }

            composable(route = CatScreen.Favourites.name) {
                FavouritesScreen(localUiState, windowSizeClass)
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

    if(screenName == CatScreen.Home.name)
        navController.popBackStack()

    navController.navigate(screenName)
}