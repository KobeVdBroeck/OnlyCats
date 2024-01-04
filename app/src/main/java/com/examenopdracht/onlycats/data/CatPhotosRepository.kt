package com.examenopdracht.onlycats.data

import com.examenopdracht.onlycats.network.CatPhoto

interface CatPhotosRepository {
    suspend fun getCatPhotos(amount: Int): List<CatPhoto>
    suspend fun savePhoto(photo: CatPhoto)
    suspend fun deletePhoto(photo: CatPhoto)
    suspend fun clearPhotos()
}