package com.examenopdracht.onlycats

import android.content.Context
import androidx.activity.ComponentActivity
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import com.examenopdracht.onlycats.ui.CatScreen
import com.examenopdracht.onlycats.ui.OnlyCatsApp
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
class LandscapeModeNavigationTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private lateinit var navController: TestNavHostController

    @Before
    fun setupCatNavHost() {
        var context = ApplicationProvider.getApplicationContext<Context>()

        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current).apply {
                navigatorProvider.addNavigator(ComposeNavigator())
            }
            OnlyCatsApp(navController = navController, windowSizeClass = WindowSizeClass.calculateFromSize(
                DpSize(1000.dp, 500.dp)
            ))
        }
    }

    @Test
    fun catNavHost_landscapeMode_showsNavRail() {
        composeTestRule.onNodeWithContentDescription("NavRail home icon").assertExists("NavRail was not or partially loaded")
        composeTestRule.onNodeWithContentDescription("NavRail favourites icon").assertExists("NavRail was not or partially loaded")
        composeTestRule.onNodeWithContentDescription("NavRail settings icon").assertExists("NavRail was not or partially loaded")
    }

    @Test
    fun catNavHost_clickNavRailFavouritesIcon_navigatesToFavourites() {
        composeTestRule.onNodeWithContentDescription("NavRail favourites icon").performClick()

        navController.assertCurrentRoute(CatScreen.Favourites.name)

        composeTestRule.onNodeWithContentDescription("NavRail home icon").performClick()
    }

    @Test
    fun catNavHost_clickNavRailFavouritesIcon_navigatesToSettings() {
        composeTestRule.onNodeWithContentDescription("NavRail settings icon").performClick()

        navController.assertCurrentRoute(CatScreen.Settings.name)

        composeTestRule.onNodeWithContentDescription("NavRail home icon").performClick()
    }
}