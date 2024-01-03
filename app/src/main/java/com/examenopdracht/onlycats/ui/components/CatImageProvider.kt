package com.examenopdracht.onlycats.ui.components

import com.examenopdracht.onlycats.network.CatPhoto

class CatPhotoProvider(limit: Int, val getNewImage: () -> Unit) {

    var onUpdate: () -> Unit = { }

    var photos = mutableListOf<CatPhoto>()
        private set

    var limit: Int
        private set


    init {
        this.limit = limit
    }

    fun getPhoto(index: Int): CatPhoto {
        if(index >= photos.size)
            return CatPhoto.Empty()

        return photos[index]
    }

    fun addPhoto(photo: CatPhoto) {
        if(limit != 0 && photos.size == limit)
            removePhoto(0)

        photos += photo
        onUpdate()
    }

    fun removePhoto(index: Int) {
        removePhoto(photos[index])
    }

    fun removePhoto(photo: CatPhoto) {
        photos -= photo
        onUpdate()
    }
}