package com.examenopdracht.onlycats.model

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import coil.ImageLoader
import coil.annotation.ExperimentalCoilApi
import coil.request.ImageRequest
import com.examenopdracht.onlycats.data.CatPhotosRepository
import com.examenopdracht.onlycats.data.api.NetworkUiState
import com.examenopdracht.onlycats.data.db.LocalUiState
import com.examenopdracht.onlycats.network.CatPhoto
import com.examenopdracht.onlycats.ui.OnlyCatsApplication
import com.examenopdracht.onlycats.ui.components.CatImageProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch


class HomeViewModel(private val apiRepository: CatPhotosRepository, private val roomRepository: CatPhotosRepository, private val context: Context) : ViewModel() {

    private var networkImageProvider: CatImageProvider = CatImageProvider(5) { viewModelScope.launch { getNewPhotos() } }
    private var localImages: List<CatPhoto> = listOf()

    var networkUiState: MutableStateFlow<NetworkUiState> = MutableStateFlow(NetworkUiState.Loading)
        public get
        private set

    var localUiState: MutableStateFlow<LocalUiState> = MutableStateFlow(LocalUiState.Loading)
        public get
        private set

    init {
        viewModelScope.launch {
            getNewPhotos()
            getNewPhotos()
            getFavourites()
        }
    }

    private suspend fun getNewPhotos() {
        try {
            val photos = apiRepository.getCatPhotos(5)

            photos.forEach {
                    it.photo = convertToBitmap(Uri.parse(it.url), context, it.width, it.height)
                    it.onImageLoaded()
                    it.save = { savePhoto(it) }
                    it.unsave = { deletePhoto(it) }
            }

            networkImageProvider.addNewPhotos(photos)
            networkUiState.value = NetworkUiState.Success(networkImageProvider)

        } catch(ex: Exception) {
            networkUiState.value = NetworkUiState.Error
            ex.printStackTrace()
        }
    }

    private fun savePhoto(photo: CatPhoto) {
        if(photo.dbId == 0)
            photo.dbId = null

        viewModelScope.launch {
            roomRepository.savePhoto(photo)
            photo.isSaved = true
            getFavourites()
        }
    }

    private fun deletePhoto(photo: CatPhoto) {
        viewModelScope.launch {
            roomRepository.deletePhoto(photo)
            photo.isSaved = false
            getFavourites()
        }
    }

    private suspend fun getFavourites() {
        localUiState.value = LocalUiState.Loading

        try {
            val photos = roomRepository.getCatPhotos(10)

            photos.forEach {
                    it.photo = convertToBitmap(Uri.parse(it.url), context, it.width, it.height)
                    it.onImageLoaded()
                    it.isSaved = true
                    it.save = { savePhoto(it) }
                    it.unsave = { deletePhoto(it) }
            }

            localImages = photos
            localUiState.value = LocalUiState.Success(photos)

        } catch(ex: Exception) {
            localUiState.value = LocalUiState.Error
            ex.printStackTrace()
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as OnlyCatsApplication)
                val apiRepository = application.container.apiRepository
                val roomRepository = application.container.roomRepository

                HomeViewModel(apiRepository, roomRepository, application.applicationContext)
            }
        }
    }
}

@OptIn(ExperimentalCoilApi::class)
suspend fun getDrawable(uri: Uri, context: Context): Drawable? {
    val imageLoader = ImageLoader.Builder(context)
        .allowHardware(false)
        .crossfade(true)
        .build()

    val request = ImageRequest.Builder(context)
        .data(uri)
        .build()
    return imageLoader.execute(request).drawable
}

suspend fun convertToBitmap(uri: Uri, context: Context, widthPixels: Int, heightPixels: Int): ImageBitmap? {
    val mutableBitmap = Bitmap.createBitmap(widthPixels, heightPixels, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(mutableBitmap)
    val drawable = getDrawable(uri, context)
    drawable?.setBounds(0, 0, widthPixels, heightPixels)
    drawable?.draw(canvas)
    return mutableBitmap.asImageBitmap()
}