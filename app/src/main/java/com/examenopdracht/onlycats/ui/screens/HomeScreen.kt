package com.examenopdracht.onlycats.ui.screens

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.examenopdracht.onlycats.data.api.NetworkUiState
import com.examenopdracht.onlycats.network.CatPhoto
import com.examenopdracht.onlycats.ui.components.CatImageProvider
import com.examenopdracht.onlycats.ui.components.CatView

// TODO app icon
@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun HomeScreen (
    catUiState: NetworkUiState,
    windowSizeClass: WindowSizeClass,
    modifier: Modifier = Modifier,
) {
    when (catUiState) {
        is NetworkUiState.Success -> ResultScreen(
            catUiState.imageProvider, catUiState.imageProvider.selectedPhoto, windowSizeClass
        )
        is NetworkUiState.Loading -> LoadingScreen()

        is NetworkUiState.Error -> ErrorScreen()
    }
}

@Composable
fun ResultScreen(imageProvider: CatImageProvider, curImage: MutableState<CatPhoto> = imageProvider.selectedPhoto, windowSizeClass: WindowSizeClass) {
        val width = with(LocalDensity.current) { LocalConfiguration.current.screenWidthDp.dp.toPx().toInt() }
        val context = LocalContext.current

        fun handleTap(offset: Offset) {
            if(offset.x < width / 5)
                imageProvider.getPrevious()
            if(offset.x > width * 4 / 5)
                imageProvider.getNext()
        }

        fun handleDoubleTap(offset: Offset) {
            if(offset.x < width / 5 || offset.x > width * 4 / 5)
                return

            if(curImage.value.id == "")
                return

            else if(curImage.value.isSaved) {
                curImage.value.unsave()
                Toast.makeText(context, "Image removed from favourites.", Toast.LENGTH_SHORT).show()
            }
            else {
                curImage.value.save()
                Toast.makeText(context, "Image added to favourites.", Toast.LENGTH_SHORT).show()
            }
        }

        Box() {
            Text("Welcome to OnlyCats!",
                fontSize = TextUnit(10f, TextUnitType.Em),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .size(500.dp)
                    .padding(start = if (windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact) 0.dp else 50.dp))

            CatListView(photo = curImage,
                onTap = { offset -> handleTap(offset) },
                onDoubleTap = { offset -> handleDoubleTap(offset) },
                windowSizeClass = windowSizeClass)
        }
    }

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CatListView(photo: MutableState<CatPhoto>, onTap: (Offset) -> Unit, onDoubleTap: (Offset) -> Unit, windowSizeClass: WindowSizeClass) {
    Box(
        modifier = Modifier
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = onTap,
                    onDoubleTap = onDoubleTap
                )
            }
            .fillMaxSize()
            .padding(
                top = 50.dp,
                bottom = if (windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact) 50.dp else 0.dp
            )
    ) {
        CatView(photo)
    }
}