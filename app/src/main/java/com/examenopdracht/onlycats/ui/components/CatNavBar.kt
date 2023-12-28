package com.examenopdracht.onlycats.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import com.examenopdracht.onlycats.ui.CatScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatNavBar(
    currentScreen: CatScreen,
    navigateTo: (screenName: String) -> Unit
) {
    Scaffold (
        bottomBar = {
            NavigationBar {
                NavigationBarItem(onClick = { navigateTo(CatScreen.Home.name) }, selected = currentScreen == CatScreen.Home,
                    icon = { Icon(Icons.Filled.Home, contentDescription = "Home icon") })

                NavigationBarItem(onClick = { navigateTo(CatScreen.Favourites.name) }, selected = currentScreen == CatScreen.Favourites,
                    icon = { Icon(Icons.Filled.Favorite, contentDescription = "Favourites icon") })

                NavigationBarItem(onClick = { navigateTo(CatScreen.Settings.name) }, selected = currentScreen == CatScreen.Settings,
                    icon = { Icon(Icons.Filled.Settings, contentDescription = "Settings icon") })
            }
        }
    ) { paddingValues ->
        // TODO
        val koenk = paddingValues
    }

}

