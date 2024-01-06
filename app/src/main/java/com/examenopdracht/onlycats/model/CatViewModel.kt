package com.examenopdracht.onlycats.model

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Environment
import android.util.Log
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
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
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream


class CatViewModel(private val apiRepository: CatPhotosRepository, private val roomRepository: CatPhotosRepository, private val context: Context) : ViewModel() {

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

    private suspend fun getFavourites() {
        localUiState.value = LocalUiState.Loading

        try {
            val photos = roomRepository.getCatPhotos(10)

            photos.forEach {
                    it.photo = convertToBitmap(Uri.parse(it.url), context, it.width, it.height)
                    it.onImageLoaded()
                    it.isSaved = true
                    it.save = { downloadPhoto("${it.id}.jpg", it.image!!.asAndroidBitmap(), context) }
                    it.unsave = { deletePhoto(it) }
            }

            localImages = photos
            localUiState.value = LocalUiState.Success(photos)

        } catch(ex: Exception) {
            localUiState.value = LocalUiState.Error
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

    private fun downloadPhoto(fileName: String, bitmap: Bitmap, context: Context) {
        val file = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), fileName)

        if(file.exists())
            throw FileAlreadyExistsException(file)

        val os: OutputStream
        try {
            os = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os)
            os.flush()
            os.close()

            MediaScannerConnection.scanFile(
                context, arrayOf(file.toString()),
                null, null
            )
        } catch (e: Exception) {
            Log.e(javaClass.simpleName, "Error writing bitmap", e)
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as OnlyCatsApplication)
                val apiRepository = application.container.apiRepository
                val roomRepository = application.container.roomRepository

                CatViewModel(apiRepository, roomRepository, application.applicationContext)
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