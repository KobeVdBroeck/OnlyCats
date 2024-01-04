package com.examenopdracht.onlycats.data

import android.content.Context
import com.examenopdracht.onlycats.data.api.NetworkCatPhotosRepository
import com.examenopdracht.onlycats.data.db.CatDatabase
import com.examenopdracht.onlycats.data.db.OfflineCatPhotosRepository
import com.examenopdracht.onlycats.network.CatApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit


interface AppContainer {
    val apiRepository: CatPhotosRepository
    val roomRepository: CatPhotosRepository
}


class DefaultAppContainer(private val context: Context) : AppContainer {

    private val BASE_URL = "https://api.thecatapi.com/v1/"

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(Json { ignoreUnknownKeys = true }.asConverterFactory("application/json".toMediaType()))
        .baseUrl(BASE_URL)
        .client(initHttpClient().build())
        .build()

    private val retrofitService: CatApiService by lazy {
        retrofit.create(CatApiService::class.java)
    }

    override val apiRepository: CatPhotosRepository by lazy {
        NetworkCatPhotosRepository(retrofitService)
    }

    override val roomRepository: CatPhotosRepository by lazy {
        OfflineCatPhotosRepository(CatDatabase.getInstance(context).catDao)
    }
}

private fun initHttpClient(): OkHttpClient.Builder {
    //val logging = HttpLoggingInterceptor()
    //logging.level = HttpLoggingInterceptor.Level.BODY

    val httpClient = OkHttpClient.Builder()
    //httpClient.addInterceptor(logging)

    return httpClient
}