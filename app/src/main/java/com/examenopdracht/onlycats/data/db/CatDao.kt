package com.examenopdracht.onlycats.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.examenopdracht.onlycats.network.CatPhoto

@Dao
interface CatDao {
    @Insert
    suspend fun insert(cat: CatPhoto)

    @Query("SELECT * FROM cats")
    suspend fun getAll(): List<CatPhoto>

    @Query("SELECT * FROM cats WHERE id = :key")
    suspend fun get(key: Int): CatPhoto?

    @Query("DELETE FROM cats")
    suspend fun clearSaved()

    @Delete
    suspend fun delete(photo: CatPhoto)
}