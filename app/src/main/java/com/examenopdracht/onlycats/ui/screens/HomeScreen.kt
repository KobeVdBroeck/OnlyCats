package com.examenopdracht.onlycats.ui.screens

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
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
import com.examenopdracht.onlycats.data.NetworkUiState
import com.examenopdracht.onlycats.network.CatPhoto
import com.examenopdracht.onlycats.ui.components.CatPhotoProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

// TODO app icon
@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun HomeScreen (
    catUiState: MutableStateFlow<NetworkUiState>,
    modifier: Modifier = Modifier
) {
    when (val state = catUiState.value) {
        is NetworkUiState.Success -> ResultScreen(
            state.imageProvider
        )
        is NetworkUiState.Loading -> LoadingScreen()

        is NetworkUiState.Error -> ErrorScreen()
    }


}

@Composable
fun LoadingScreen() {
    Text("EPIC LOADING MOMENT")
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ResultScreen(imageProvider: CatPhotoProvider) {
        var pageCount = remember { imageProvider.limit }
        val width = with(LocalDensity.current) {LocalConfiguration.current.screenWidthDp.dp.toPx().toInt()}
        val context = LocalContext.current
        val curPage = remember { mutableIntStateOf(0) }
        val curImage = { imageProvider.getPhoto(curPage.value) }

        fun getNewImage() {
            if(curImage().id == "")
                imageProvider.getNewImage()
        }

        fun handleTap(offset: Offset) {
            if(curImage == null)
                return

            if(offset.x < width / 5)
                curPage.value -= 1
            if(offset.x > width * 4 / 5)
                curPage.value += 1
        }

        fun handleDoubleTap(offset: Offset) {
            if(offset.x < width / 5 || offset.x > width * 4 / 5)
                return

            if(curImage().id == "")
                return

            else if(curImage().isSaved) {
                curImage().unsave()
                Toast.makeText(context, "Image removed from favourites.", Toast.LENGTH_SHORT).show()
            }
            else {
                curImage().save()
                Toast.makeText(context, "Image added to favourites.", Toast.LENGTH_SHORT).show()
            }
        }

        Scaffold {paddingValues ->
            val temp = paddingValues

            Text("Welcome to OnlyCats!", fontSize = TextUnit(10f, TextUnitType.Em), textAlign = TextAlign.Center, modifier = Modifier.size(500.dp))

            CatListView(photo = curImage(),
                limit = imageProvider.limit,
                currentPage = curPage.value,
                onTap = { offset -> handleTap(offset) },
                onDoubleTap = { offset -> handleDoubleTap(offset) },
                onLimitReached = { getNewImage() })
        }
    }

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CatListView(photo: CatPhoto, limit: Int, currentPage: Int, onTap: (Offset) -> Unit, onDoubleTap: (Offset) -> Unit, onLimitReached: () -> Unit) {
    val pagerState = rememberPagerState(pageCount = { limit })

    if(pagerState.currentPage != currentPage) {
        if(currentPage >= limit)
            onLimitReached()

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
    ) {
        if(photo.id == "")
            LoadingImageView()
        else
            CatView(photo)
    }
}

@Composable
fun CatView(image: CatPhoto) {
    if(image.image == null) {
        LoadingImageView()
    }
    else {
        Image(image.image!!, "Cat", modifier = Modifier.fillMaxSize())
    }
}

@Composable
fun LoadingImageView() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center,) {
        CircularProgressIndicator(
            modifier = Modifier.width(64.dp),
            color = MaterialTheme.colorScheme.secondary,
        )
    }
}

@Composable
fun ErrorScreen() {
    Text("EPIC ERROR MOMENT")
}