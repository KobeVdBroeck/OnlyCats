package com.examenopdracht.onlycats.ui

import androidx.lifecycle.ViewModel
import com.examenopdracht.onlycats.data.CatUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class CatViewModel : ViewModel() {
    // TODO
    private val _uiState = MutableStateFlow(CatUiState())
    val uiState: StateFlow<CatUiState> = _uiState.asStateFlow()
}