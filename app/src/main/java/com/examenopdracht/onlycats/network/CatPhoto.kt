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

    override fun equals(other: Any?): Boolean {
        if(other == null || other::class.simpleName != CatPhoto::class.simpleName)
            return false

        val otherPhoto = other as CatPhoto

        return this.url == otherPhoto.url && (this.image == null) == (otherPhoto.image == null)
    }

    override fun hashCode(): Int {
        return this.url.hashCode()
    }

    companion object Factory {
        fun empty(): CatPhoto {
            return CatPhoto(0, "", "", 0, 0)
        }
    }
}