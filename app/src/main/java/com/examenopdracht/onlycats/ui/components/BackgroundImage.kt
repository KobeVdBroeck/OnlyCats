package com.examenopdracht.onlycats.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.examenopdracht.onlycats.R


@Composable
fun BackgroundImage() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        val backgroundImage = painterResource(id = R.drawable.bg)


        Box(
            modifier = Modifier
                .fillMaxSize()
                .paint(backgroundImage, contentScale = ContentScale.Crop)
        )
    }
}