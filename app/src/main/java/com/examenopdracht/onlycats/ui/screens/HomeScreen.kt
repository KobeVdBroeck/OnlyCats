package com.examenopdracht.onlycats.ui.screens

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

// TODO app icon
@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun HomeScreen (
    catUiState: MutableStateFlow<NetworkUiState>,
    windowSizeClass: WindowSizeClass,
    modifier: Modifier = Modifier,
) {
    when (val state = catUiState.value) {
        is NetworkUiState.Success -> ResultScreen(
            state.imageProvider, windowSizeClass
        )
        is NetworkUiState.Loading -> LoadingScreen()

        is NetworkUiState.Error -> ErrorScreen()
    }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ResultScreen(imageProvider: CatImageProvider, windowSizeClass: WindowSizeClass) {
        val width = with(LocalDensity.current) { LocalConfiguration.current.screenWidthDp.dp.toPx().toInt() }
        val context = LocalContext.current
        val curPage = remember { mutableIntStateOf(0) }
        val curImage = imageProvider.selectedPhoto

        fun handleTap(offset: Offset) {
            if(curImage == null)
                return

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
            Text("Welcome to OnlyCats!", fontSize = TextUnit(10f, TextUnitType.Em), textAlign = TextAlign.Center, modifier = Modifier.size(500.dp))

            CatListView(photo = curImage.value,
                limit = imageProvider.limit,
                currentPage = curPage.value,
                onTap = { offset -> handleTap(offset) },
                onDoubleTap = { offset -> handleDoubleTap(offset) },)
        }
    }

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CatListView(photo: CatPhoto, limit: Int, currentPage: Int, onTap: (Offset) -> Unit, onDoubleTap: (Offset) -> Unit) {
    val pagerState = rememberPagerState(pageCount = { limit })

    if(pagerState.currentPage != currentPage) {
        runBlocking {
            launch { pagerState.scrollToPage(currentPage)}
        }
    }

    HorizontalPager(
        contentPadding = PaddingValues(vertical = 50.dp),
        state = pagerState,
        modifier = Modifier
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = onTap,
                    onDoubleTap = onDoubleTap
                )
            }
            .fillMaxSize()
            .padding(top = 85.dp)
    ) {
        CatView(photo)
    }
}