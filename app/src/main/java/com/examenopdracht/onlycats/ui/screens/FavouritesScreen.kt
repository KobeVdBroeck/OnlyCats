package com.examenopdracht.onlycats.ui.screens

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.examenopdracht.onlycats.R
import com.examenopdracht.onlycats.data.LocalUiState
import com.examenopdracht.onlycats.network.CatPhoto
import kotlinx.coroutines.flow.MutableStateFlow

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun FavouritesScreen(
    state: MutableStateFlow<LocalUiState>,
    modifier: Modifier = Modifier
) {
    when (val state = state.value) {
        is LocalUiState.Success -> FavouritesScreen(state.photos)
        is LocalUiState.Loading -> LoadingScreen()
        is LocalUiState.Error -> ErrorScreen()
    }
}

@Composable
fun FavouritesScreen(photos: List<CatPhoto>) {
    var selectedImage by remember { mutableStateOf(CatPhoto.Empty()) }
    val width = with(LocalDensity.current) { LocalConfiguration.current.screenWidthDp.dp.toPx().toInt()}
    val context = LocalContext.current

    fun handleTap(offset: Offset) {
        if(offset.x < width / 5 || offset.x > width * 4 / 5)
            selectedImage = CatPhoto.Empty()
    }

    fun handleDoubleTap(offset: Offset) {
        if(offset.x < width / 5 || offset.x > width * 4 / 5)
            return

        println(selectedImage.isSaved)

        if(selectedImage.isSaved) {
            selectedImage.unsave()
            Toast.makeText(context, "Image removed from favourites.", Toast.LENGTH_SHORT).show()
        }
        else {
            selectedImage.save()
            Toast.makeText(context, "Image added to favourites.", Toast.LENGTH_SHORT).show()
        }
    }

    if(selectedImage.url == "") {
        ImageGrid(photos = photos, onImageClick = { image -> selectedImage = image })
    }
    else {
        Box(
            modifier = Modifier.pointerInput(Unit) {
                detectTapGestures(
                    onTap = { offset -> handleTap(offset) },
                    onDoubleTap = { offset -> handleDoubleTap(offset) }
                )
            }
        ) {
            CatView(image = selectedImage!!)
        }
    }
}

@Composable
fun ImageGrid(photos: List<CatPhoto>, onImageClick: (image: CatPhoto) -> Unit) {
    LazyVerticalGrid(
        columns = GridCells.FixedSize(size = 128.dp),
        contentPadding = PaddingValues(4.dp),
    ) {
        items(photos) { photo ->
            GridItem(photo = photo, onImageClick)
        }
    }
}

@Composable
fun BG() {
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

@Composable
fun GridItem(photo: CatPhoto, onImageClick: (image: CatPhoto) -> Unit) {
    Box(
        modifier = Modifier
            .padding(4.dp)
            .clip(MaterialTheme.shapes.large)
            .background(MaterialTheme.colorScheme.background)
            .clickable { onImageClick(photo) }
            //.ripple(color = MaterialTheme.colorScheme.primary, radius = 4.dp)
            .padding(8.dp)
            .aspectRatio(1f)
    ) {

        var imageLoaded = remember { mutableStateOf(photo.image != null) }

        if(!imageLoaded.value) {
            photo.onImageLoaded = { imageLoaded.value = true }
            Image(painterResource(id = R.drawable.cover2),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(128.dp)
                    .aspectRatio(1f))
        }
        else {
            Image(photo.image!!,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(128.dp)
                    .aspectRatio(1f))
        }
    }
}