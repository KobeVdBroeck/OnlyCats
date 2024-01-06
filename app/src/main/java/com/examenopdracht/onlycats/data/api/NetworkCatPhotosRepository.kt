package com.examenopdracht.onlycats.data.api

import com.examenopdracht.onlycats.data.CatPhotosRepository
import com.examenopdracht.onlycats.network.CatApiService
import com.examenopdracht.onlycats.network.CatPhoto

class NetworkCatPhotosRepository(
    private val catApiService: CatApiService
) : CatPhotosRepository {
    override suspend fun getCatPhotos(amount: Int): List<CatPhoto> {
        return catApiService.getPhotos(limit = amount)
    }
    override suspend fun savePhoto(photo: CatPhoto) {
        throw Exception("Cannot save photos on external API")
    }

    override suspend fun deletePhoto(photo: CatPhoto) {
        throw Exception("Cannot delete photos on external API")
    }

    override suspend fun clearPhotos() {
        throw Exception("Cannot delete photos on external API")
    }
}