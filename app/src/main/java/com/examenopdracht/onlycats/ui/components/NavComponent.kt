package com.examenopdracht.onlycats.ui.components

import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import com.examenopdracht.onlycats.ui.CatScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavComponent(
    currentScreen: CatScreen,
    navigateTo: (screenName: String) -> Unit,
    windowSizeClass: WindowSizeClass
) {
    Scaffold (
        bottomBar = {
            if(windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact) {
                BottomAppBar {
                    NavBar(currentScreen, navigateTo)
                }
            }
        }

    ) { paddingValues ->
        // TODO
        val koenk = paddingValues

        if(windowSizeClass.widthSizeClass != WindowWidthSizeClass.Compact) {
            NavRail(currentScreen, navigateTo)
        }
    }

}

