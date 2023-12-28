package com.examenopdracht.onlycats.network

import kotlinx.serialization.Serializable


@Serializable
data class CatPhoto (
    val id: String,
    val breeds: Array<String>,
    val url: String,
    val width: Int,
    val height: Int
)