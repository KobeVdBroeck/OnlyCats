package com.examenopdracht.onlycats.data

import com.examenopdracht.onlycats.ui.components.CatPhotoProvider

sealed interface NetworkUiState {
    data class Success(val imageProvider: CatPhotoProvider) : NetworkUiState
    object Error : NetworkUiState
    object Loading : NetworkUiState
}