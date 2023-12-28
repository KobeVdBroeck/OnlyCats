package com.examenopdracht.onlycats.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.examenopdracht.onlycats.ui.theme.OnlyCatsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OnlyCatsTheme {
                OnlyCatsApp()
            }
        }
    }
}