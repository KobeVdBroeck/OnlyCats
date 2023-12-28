package com.examenopdracht.onlycats.data

import com.examenopdracht.onlycats.network.CatApiService
import com.examenopdracht.onlycats.network.CatPhoto

interface CatPhotosRepository {
    suspend fun getCatPhotos(): List<CatPhoto>
}

class NetworkCatPhotosRepository(
    private val catApiService: CatApiService
) : CatPhotosRepository {
    override suspend fun getCatPhotos(): List<CatPhoto> = catApiService.getPhotos(limit = 5)
}