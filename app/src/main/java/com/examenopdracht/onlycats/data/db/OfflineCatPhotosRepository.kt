package com.examenopdracht.onlycats.data.db

import com.examenopdracht.onlycats.data.CatPhotosRepository
import com.examenopdracht.onlycats.network.CatPhoto

class OfflineCatPhotosRepository(private val catDao: CatDao): CatPhotosRepository {
    override suspend fun getCatPhotos(amount: Int): List<CatPhoto> {
        return catDao.getAll()
    }

    override suspend fun savePhoto(photo: CatPhoto) {
        return catDao.insert(photo)
    }

    override suspend fun deletePhoto(photo: CatPhoto) {
        catDao.delete(photo)
    }

    override suspend fun clearPhotos() {
        catDao.clearSaved()
    }
}