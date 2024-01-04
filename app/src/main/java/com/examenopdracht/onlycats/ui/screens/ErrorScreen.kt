package com.examenopdracht.onlycats.ui.screens

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp

@Composable
fun ErrorScreen() {
    Text("Something went wrong. Check you connection or try again later.", fontSize = TextUnit(10f, TextUnitType.Em), textAlign = TextAlign.Center, modifier = Modifier.size(500.dp))
}