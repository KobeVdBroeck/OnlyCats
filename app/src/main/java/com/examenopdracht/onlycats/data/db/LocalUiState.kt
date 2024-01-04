package com.examenopdracht.onlycats.data.db

import com.examenopdracht.onlycats.network.CatPhoto

sealed interface LocalUiState {
    data class Success(val photos: List<CatPhoto>) : LocalUiState
    object Error : LocalUiState
    object Loading : LocalUiState
}