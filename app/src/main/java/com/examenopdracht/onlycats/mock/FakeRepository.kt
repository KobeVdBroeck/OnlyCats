package com.examenopdracht.onlycats.mock

import com.examenopdracht.onlycats.data.CatPhotosRepository
import com.examenopdracht.onlycats.network.CatPhoto

class FakeRepository : CatPhotosRepository {

    val dataSource = FakeDataSource()

    override suspend fun getCatPhotos(amount: Int): List<CatPhoto> {
        return dataSource.images
    }

    override suspend fun savePhoto(photo: CatPhoto) {
        dataSource.images += photo
        dataSource.images = dataSource.images.plusElement(photo)
    }

    override suspend fun deletePhoto(photo: CatPhoto) {
        dataSource.images -= photo
    }

    override suspend fun clearPhotos() {
        dataSource.images = mutableListOf()
    }
}

