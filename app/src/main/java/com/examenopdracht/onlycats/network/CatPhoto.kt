package com.examenopdracht.onlycats.network

import androidx.compose.ui.graphics.ImageBitmap
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient



@Serializable
@Entity(tableName = "cats")
data class CatPhoto (
    @PrimaryKey(autoGenerate = true)
    @Transient
    var dbId: Int? = 0,

    val id: String,

    // val breeds: Array<String>, TODO change type
    val url: String,
    val width: Int,
    val height: Int,

    @Transient
    var image: ImageBitmap? = null,
) {
    var photo: ImageBitmap?
        set(value) {
            image = value
            onImageLoaded()
        }
        get() {
            return image
        }

    @Transient
    @Ignore
    var isSaved = false

    @Transient
    @Ignore
    var onImageLoaded: () -> Unit = { }

    @Transient
    @Ignore
    var save: () -> Unit = { }

    @Transient
    @Ignore
    var unsave: () -> Unit = { }

    companion object Factory {
        fun Empty(): CatPhoto {
            return CatPhoto(0, "", "", 0, 0)
        }
    }
}