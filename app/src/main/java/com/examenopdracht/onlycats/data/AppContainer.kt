package com.examenopdracht.onlycats.data

import com.examenopdracht.onlycats.network.CatApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit


interface AppContainer {
    val catPhotosRepository: CatPhotosRepository
}


class DefaultAppContainer : AppContainer {

    private val BASE_URL = "https://api.thecatapi.com/v1/"

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(BASE_URL)
        .client(initHttpClient().build())
        .build()

    private val retrofitService: CatApiService by lazy {
        retrofit.create(CatApiService::class.java)
    }

    override val catPhotosRepository: CatPhotosRepository by lazy {
        NetworkCatPhotosRepository(retrofitService)
    }
}

private fun initHttpClient(): OkHttpClient.Builder {
    val logging = HttpLoggingInterceptor()
    logging.level = HttpLoggingInterceptor.Level.BODY

    val httpClient = OkHttpClient.Builder()
    httpClient.addInterceptor(logging)

    return httpClient
}