package com.examenopdracht.onlycats.data

import com.examenopdracht.onlycats.network.CatPhoto

sealed interface CatUiState {
    data class Success(val photos: List<CatPhoto>) : CatUiState
    object Error : CatUiState
    object Loading : CatUiState
}