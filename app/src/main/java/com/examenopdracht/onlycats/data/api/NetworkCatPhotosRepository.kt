package com.examenopdracht.onlycats.data.api

import com.examenopdracht.onlycats.data.CatPhotosRepository
import com.examenopdracht.onlycats.network.CatApiService
import com.examenopdracht.onlycats.network.CatPhoto

class NetworkCatPhotosRepository(
    private val catApiService: CatApiService
) : CatPhotosRepository {
    override suspend fun getCatPhotos(amount: Int): List<CatPhoto> = catApiService.getPhotos(limit = 5)
    override suspend fun savePhoto(photo: CatPhoto) { }

    override suspend fun deletePhoto(photo: CatPhoto) { }

    override suspend fun clearPhotos() {
        throw Exception("Cannot delete photos on an external API")
    }
}