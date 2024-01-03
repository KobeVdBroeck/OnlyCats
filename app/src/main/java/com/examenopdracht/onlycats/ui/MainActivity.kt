package com.examenopdracht.onlycats.ui

import android.database.CursorWindow
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.examenopdracht.onlycats.ui.theme.OnlyCatsTheme
import java.lang.reflect.Field


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setWindowLimit(10) // TODO get from config

        setContent {
            OnlyCatsTheme {
                OnlyCatsApp()
            }
        }
    }

    // Set the limit for CursorWindow size
    private fun setWindowLimit(sizeMb: Int) {
        try {
            val field: Field = CursorWindow::class.java.getDeclaredField("sCursorWindowSize")
            field.setAccessible(true)
            field.set(null, sizeMb * 1024 * 1024)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}