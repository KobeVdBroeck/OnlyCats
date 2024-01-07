package com.examenopdracht.onlycats.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
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
import com.examenopdracht.onlycats.model.CatViewModel
import com.examenopdracht.onlycats.ui.components.NavComponent
import com.examenopdracht.onlycats.ui.screens.FavouritesScreen
import com.examenopdracht.onlycats.ui.screens.HomeScreen

enum class CatScreen {
    Home,
    Favourites,
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun OnlyCatsApp(
    navController: NavHostController = rememberNavController(),
    viewModel: CatViewModel = viewModel(factory = CatViewModel.Factory),
    windowSizeClass: WindowSizeClass
) {
    val backStackEntry by navController.currentBackStackEntryAsState()

    val currentScreen = CatScreen.valueOf(
        backStackEntry?.destination?.route ?: CatScreen.Home.name
    )

    Scaffold{
        val networkUiState by viewModel.networkUiState.collectAsState()
        val localUiState by viewModel.localUiState.collectAsState()

        NavComponent(currentScreen, { page -> goToPage(navController, currentScreen, page) }, windowSizeClass)

        NavHost(
            navController = navController,
            startDestination = CatScreen.Home.name,
            modifier = Modifier.padding(start = if (windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact) 0.dp else 85.dp,
                                        bottom = if (windowSizeClass.widthSizeClass != WindowWidthSizeClass.Compact) 0.dp else 50.dp)) {
            composable(route = CatScreen.Home.name) {
                HomeScreen(networkUiState, windowSizeClass)
            }

            composable(route = CatScreen.Favourites.name) {
                FavouritesScreen(localUiState)
            }
        }
    }
}

fun goToPage(navController: NavHostController, currentScreen: CatScreen, screenName: String) {
    if(currentScreen.name == screenName)
        return

    if(screenName == CatScreen.Home.name)
        navController.popBackStack()

    navController.navigate(screenName)
}