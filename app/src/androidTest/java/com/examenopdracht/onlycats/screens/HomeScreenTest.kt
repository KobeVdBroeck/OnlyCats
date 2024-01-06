package com.examenopdracht.onlycats.screens

import android.content.Context
import androidx.activity.ComponentActivity
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.doubleClick
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performMouseInput
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import com.examenopdracht.onlycats.mock.FakeCatViewModel
import com.examenopdracht.onlycats.ui.OnlyCatsApp
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalTestApi::class)
class HomeScreenTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        var navController: TestNavHostController

        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current).apply {
                navigatorProvider.addNavigator(ComposeNavigator())
            }
            OnlyCatsApp(navController = navController, viewModel = FakeCatViewModel(context), windowSizeClass = WindowSizeClass.calculateFromSize(
                DpSize(500.dp, 1000.dp)
            ))
        }
    }

    @Test
    fun homeScreen_noUserInput_imageIsLoaded() {
        composeTestRule.waitUntilAtLeastOneExists(hasContentDescription("Cat"), 10000L)

        composeTestRule.onNodeWithContentDescription("Cat").assertIsDisplayed()
    }

    @Test
    fun homeScreen_doubleTap_imageIsSaved() {
        composeTestRule.waitUntilAtLeastOneExists(hasContentDescription("Cat"), 10000L)

        composeTestRule.onNodeWithContentDescription("Cat").performMouseInput {
            this.doubleClick(Offset(centerX, centerY))
        }

        // Does not work because toasts are not visible in the app's DOM tree
        composeTestRule.onNodeWithText("Image added to favourites.", true).assertExists()
    }
}