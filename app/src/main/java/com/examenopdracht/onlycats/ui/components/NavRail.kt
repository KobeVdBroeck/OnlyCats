package com.examenopdracht.onlycats.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.runtime.Composable
import com.examenopdracht.onlycats.ui.CatScreen

@Composable
fun NavRail(currentScreen: CatScreen, navigateTo: (screenName: String) -> Unit) {
    NavigationRail {
        NavigationRailItem(onClick = { navigateTo(CatScreen.Home.name) }, selected = currentScreen == CatScreen.Home,
            icon = { Icon(Icons.Filled.Home, contentDescription = "NavRail home icon") })

        NavigationRailItem(onClick = { navigateTo(CatScreen.Favourites.name) }, selected = currentScreen == CatScreen.Favourites,
            icon = { Icon(Icons.Filled.Favorite, contentDescription = "NavRail favourites icon") })

        NavigationRailItem(onClick = { navigateTo(CatScreen.Settings.name) }, selected = currentScreen == CatScreen.Settings,
            icon = { Icon(Icons.Filled.Settings, contentDescription = "NavRail settings icon") })
    }
}