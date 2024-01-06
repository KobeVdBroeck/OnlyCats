package com.examenopdracht.onlycats.ui.components

import android.annotation.SuppressLint
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import com.examenopdracht.onlycats.ui.CatScreen

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
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

    ) {
        if(windowSizeClass.widthSizeClass != WindowWidthSizeClass.Compact) {
            NavRail(currentScreen, navigateTo)
        }
    }

}

