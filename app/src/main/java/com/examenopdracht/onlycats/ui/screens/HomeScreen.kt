package com.examenopdracht.onlycats.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.examenopdracht.onlycats.data.CatUiState
import com.examenopdracht.onlycats.network.CatPhoto
import com.examenopdracht.onlycats.ui.components.CatCard

@Composable
fun HomeScreen (
    catUiState: CatUiState,
    modifier: Modifier = Modifier
) {
    when (catUiState) {
        is CatUiState.Loading -> LoadingScreen()
        is CatUiState.Success -> ResultScreen(
            catUiState.photos
        )

        is CatUiState.Error -> ErrorScreen()
    }
}

@Composable
fun LoadingScreen() {
    Text("EPIC LOADING MOMENT")
}

@Composable
fun ResultScreen(photos: List<CatPhoto>) {
    LazyColumn {
        item {
            Row(
                modifier = Modifier.fillMaxWidth()
                    .wrapContentHeight()
                    .padding(vertical = 25.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "\ud83d\udc31  Cats",
                )
            }
        }
        items(photos) { cat ->
            CatCard(cat = cat)
        }
    }

}

@Composable
fun ErrorScreen() {
    Text("EPIC ERROR MOMENT")
}
