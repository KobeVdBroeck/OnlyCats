package com.examenopdracht.onlycats.network

import retrofit2.http.GET
import retrofit2.http.Query

private val API_KEY = "live_RiKl3nNNrspoqOMWu05kBs89ElUCI5pB681APi8fNoax2DrweFh3FQf1z7N9YwBG"

interface CatApiService {
    @GET("images/search")
    suspend fun getPhotos(@Query("api_key") apiKey: String = API_KEY, @Query("limit") limit: Int): List<CatPhoto>
}