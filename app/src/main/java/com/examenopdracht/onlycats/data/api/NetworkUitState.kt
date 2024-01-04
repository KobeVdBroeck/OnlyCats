package com.examenopdracht.onlycats.data.api

import com.examenopdracht.onlycats.ui.components.CatImageProvider

sealed interface NetworkUiState {
    data class Success(val imageProvider: CatImageProvider) : NetworkUiState
    object Error : NetworkUiState
    object Loading : NetworkUiState
}