package com.examenopdracht.onlycats.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.examenopdracht.onlycats.data.CatPhotosRepository
import com.examenopdracht.onlycats.data.CatUiState
import kotlinx.coroutines.launch


class CatViewModel(private val catPhotosRepository: CatPhotosRepository) : ViewModel() {
    // TODO
    var catUiState: CatUiState by mutableStateOf(CatUiState.Loading)
        public get
        private set

    init {
        getCatPhotos()
    }

    private fun getCatPhotos() {
        viewModelScope.launch {
            catUiState = CatUiState.Loading

            try {
                val photos = catPhotosRepository.getCatPhotos()
                println(photos.count())
                catUiState = CatUiState.Success(photos)
            } catch(ex: Exception) {
                catUiState = CatUiState.Error
                ex.printStackTrace()
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as OnlyCatsApplication)
                val catPhotosRepository = application.container.catPhotosRepository
                CatViewModel(catPhotosRepository = catPhotosRepository)
            }
        }
    }
}