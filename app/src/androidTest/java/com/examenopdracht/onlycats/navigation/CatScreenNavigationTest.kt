package com.examenopdracht.onlycats.navigation

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
import com.examenopdracht.onlycats.assertCurrentRoute
import com.examenopdracht.onlycats.ui.CatScreen
import com.examenopdracht.onlycats.ui.OnlyCatsApp
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
class CatScreenNavigationTest {
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
            OnlyCatsApp(navController = navController, windowSizeClass = WindowSizeClass.calculateFromSize(DpSize(500.dp, 1000.dp)))
        }
    }

    @Test
    fun catNavHost_verifyStartDestination() {
        navController.assertCurrentRoute(CatScreen.Home.name)
    }

    @Test
    fun catNavHost_showsNavBar() {
        composeTestRule.onNodeWithContentDescription("NavBar home icon").assertExists("NavBar was not or partially loaded")
        composeTestRule.onNodeWithContentDescription("NavBar favourites icon").assertExists("NavBar was not or partially loaded")
    }

    @Test
    fun catNavHost_clickNavBarFavouritesIcon_navigatesToFavourites() {
        composeTestRule.onNodeWithContentDescription("NavBar favourites icon").performClick()

        navController.assertCurrentRoute(CatScreen.Favourites.name)

        composeTestRule.onNodeWithContentDescription("NavBar home icon").performClick()
    }
}
