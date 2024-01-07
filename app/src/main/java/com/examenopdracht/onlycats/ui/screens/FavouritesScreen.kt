package com.examenopdracht.onlycats.ui.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.examenopdracht.onlycats.data.db.LocalUiState
import com.examenopdracht.onlycats.network.CatPhoto
import com.examenopdracht.onlycats.ui.components.CatView

@Composable
fun FavouritesScreen(
    state: LocalUiState,
) {
    when (state) {
        is LocalUiState.Success -> FavouritesScreen(state.photos)
        is LocalUiState.Loading -> LoadingScreen()
        is LocalUiState.Error -> ErrorScreen()
    }
}

@Composable
fun FavouritesScreen(photos: List<CatPhoto>) {
    val selectedImage = remember { mutableStateOf(CatPhoto.empty()) }
    val width = with(LocalDensity.current) { LocalConfiguration.current.screenWidthDp.dp.toPx().toInt()}
    val context = LocalContext.current

    fun handleTap(offset: Offset) {
        val index = photos.indexOf(selectedImage.value)

        if(offset.x < width / 5) {
            if(index == 0)
                return

            selectedImage.value = photos[photos.indexOf(selectedImage.value) - 1]
        }
        else if(offset.x > width * 4 / 5) {
            if(index == (photos.count() - 1))
                return

            selectedImage.value = photos[photos.indexOf(selectedImage.value) + 1]
        }

        else selectedImage.value = CatPhoto.empty()
    }

    fun handleDoubleTap(offset: Offset) {
        if(offset.x < width / 5 || offset.x > width * 4 / 5)
            return

        selectedImage.value.unsave()
        Toast.makeText(context, "Image removed from favourites.", Toast.LENGTH_SHORT).show()
    }

    fun handleHold(offset: Offset) {
        if(offset.x < width / 5 || offset.x > width * 4 / 5)
            return

        try {
            selectedImage.value.save()
            Toast.makeText(context, "Image downloaded.", Toast.LENGTH_SHORT).show()
        }
        catch(ex: FileAlreadyExistsException) {
            Toast.makeText(context, "Image is already downloaded.", Toast.LENGTH_SHORT).show()
        }
    }

    if(selectedImage.value.url == "") {
        ImageGrid(photos = photos, onImageClick = { image -> selectedImage.value = image })
    }
    else {
        Box(
            modifier = Modifier.pointerInput(Unit) {
                detectTapGestures(
                    onTap = { offset -> handleTap(offset) },
                    onDoubleTap = { offset -> handleDoubleTap(offset) },
                    onLongPress = { offset -> handleHold(offset) }
                )
            }
        ) {
            CatView(selectedImage)
        }
    }
}

@Composable
fun ImageGrid(photos: List<CatPhoto>, onImageClick: (image: CatPhoto) -> Unit) {
    LazyVerticalGrid(
        columns = GridCells.FixedSize(size = 128.dp),
        contentPadding = PaddingValues(4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.padding(bottom = 33.dp)
    ) {
        items(photos) { photo ->
            GridItem(photo = photo, onImageClick)
        }
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

        val imageLoaded = remember { mutableStateOf(photo.image != null) }

        if(!imageLoaded.value) {
            photo.onImageLoaded = { imageLoaded.value = true }
            LoadingScreen()
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