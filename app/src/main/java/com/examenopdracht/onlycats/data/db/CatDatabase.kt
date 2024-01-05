package com.examenopdracht.onlycats.data.db

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.examenopdracht.onlycats.network.CatPhoto
import java.io.ByteArrayOutputStream

@Database(entities = [CatPhoto::class], version = 7, exportSchema = false)
@TypeConverters(ImageConverter::class)
abstract class CatDatabase: RoomDatabase() {
    abstract val catDao: CatDao

    companion object {
        @Volatile
        private var INSTANCE: CatDatabase? = null

        fun getInstance(context: Context): CatDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if(instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        CatDatabase::class.java,
                        "cat_database"
                    ).fallbackToDestructiveMigration().build()

                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}

public class ImageConverter {
    @TypeConverter
    fun fromImageBitmap(bitmap: ImageBitmap): ByteArray {
        var currentQuality = 100
        var currentBytes: Int = -1
        val sizeLimit: Int = 10 * 1024 * 1024 // TODO get from config

        var outputStream = ByteArrayOutputStream()

        while(currentBytes < 0 || currentBytes > sizeLimit / 8) {
            outputStream = ByteArrayOutputStream()
            bitmap.asAndroidBitmap().compress(Bitmap.CompressFormat.JPEG, currentQuality, outputStream)

            currentBytes = outputStream.size()

            // Decrease quality by 40%
            currentQuality = (currentQuality * 0.6).toInt()

            if(currentQuality <= 0)
                throw Exception("Could not save image")
        }

        return outputStream.toByteArray()
    }

    @TypeConverter
    fun toImageBitmap(byteArray: ByteArray): ImageBitmap {
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size).asImageBitmap()
    }
}