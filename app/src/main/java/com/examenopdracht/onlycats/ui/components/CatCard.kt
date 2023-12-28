package com.examenopdracht.onlycats.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.examenopdracht.onlycats.network.CatPhoto

@Composable
fun CatCard(cat: CatPhoto) {
    Card() {
        AsyncImage(
            model = cat.url,
            contentDescription = null,
        )
        Row(horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier.fillMaxWidth().padding(8.dp)) {
            FloatingActionButton(onClick = {  }) {
                Icon(Icons.Filled.Add, "Floating action button.")
            }
            FloatingActionButton(onClick = {  }) {
                Icon(Icons.Filled.Add, "Floating action button.")
            }
            FloatingActionButton(onClick = {  }) {
                Icon(Icons.Filled.Add, "Floating action button.")
            }
            FloatingActionButton(onClick = {  }) {
                Icon(Icons.Filled.Add, "Floating action button.")
            }
        }
    }

}