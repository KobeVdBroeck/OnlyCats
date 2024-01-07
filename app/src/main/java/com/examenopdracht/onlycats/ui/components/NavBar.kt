package com.examenopdracht.onlycats.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.examenopdracht.onlycats.ui.CatScreen

@Composable
fun NavBar(
    currentScreen: CatScreen,
    navigateTo: (screenName: String) -> Unit
) {
        NavigationBar {
            NavigationBarItem(onClick = { navigateTo(CatScreen.Home.name) }, selected = currentScreen == CatScreen.Home,
                icon = { Icon(Icons.Filled.Home, contentDescription = "NavBar home icon") },
                label = { Text(text = "Home") })

            NavigationBarItem(onClick = { navigateTo(CatScreen.Favourites.name) }, selected = currentScreen == CatScreen.Favourites,
                icon = { Icon(Icons.Filled.Favorite, contentDescription = "NavBar favourites icon") },
                label = { Text(text = "Favourites") })
        }
}

