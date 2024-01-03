package com.examenopdracht.onlycats.data

import com.examenopdracht.onlycats.network.CatApiService
import com.examenopdracht.onlycats.network.CatPhoto

interface CatPhotosRepository {
    suspend fun getCatPhotos(amount: Int): List<CatPhoto>
    suspend fun savePhoto(photo: CatPhoto)
    suspend fun deletePhoto(photo: CatPhoto)
    suspend fun clearPhotos()
}

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