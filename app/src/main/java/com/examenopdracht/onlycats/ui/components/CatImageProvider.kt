package com.examenopdracht.onlycats.ui.components

import androidx.compose.runtime.mutableStateOf
import com.examenopdracht.onlycats.network.CatPhoto

class CatImageProvider(limit: Int, var onListsSwapped: () -> Unit) {
    private var photos1 = mutableListOf<CatPhoto>()
    private var photos2 = mutableListOf<CatPhoto>()

    private var currentIndex = 0
    private var currentPhotos = photos1

    var selectedPhoto = mutableStateOf(CatPhoto.Empty())

    var limit: Int
        private set


    init {
        this.limit = limit
    }

    fun getNext() {
        currentIndex += 1

        if(currentIndex == limit) {
            currentPhotos = if (currentPhotos == photos1) photos2 else photos1
            onListsSwapped()
            currentIndex = 0
        }

        selectedPhoto.value = currentPhotos[currentIndex]
    }

    fun getPrevious() {
        currentIndex -= 1

        if(currentIndex < 0) {
            currentIndex = 0
        }

        selectedPhoto.value = currentPhotos[currentIndex]
    }

    fun addNewPhotos(list: List<CatPhoto>) {
        var listToAddTo =
            if (photos1.isEmpty()) photos1 else
            if (currentPhotos == photos1) photos2 else photos1

        listToAddTo.clear()
        listToAddTo.addAll(list)
    }
}