package com.examenopdracht.onlycats

import androidx.navigation.NavController
import org.junit.Assert.assertEquals

fun NavController.assertCurrentRoute(expectedRoute: String) {
    assertEquals(expectedRoute, currentBackStackEntry?.destination?.route)
}