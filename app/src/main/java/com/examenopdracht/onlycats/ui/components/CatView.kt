package com.examenopdracht.onlycats.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.examenopdracht.onlycats.network.CatPhoto
import com.examenopdracht.onlycats.ui.screens.LoadingScreen

@Composable
fun CatView(image: CatPhoto) {
    if(image.image == null) {
        LoadingScreen()
    }
    else {
        Image(image.image!!, "Cat", modifier = Modifier.fillMaxSize())
    }
}