package com.example.mangareadapp.api

import com.google.gson.annotations.SerializedName

data class MangaDetailResponse(
    val title: String,
    val description: String,
    val genre: String,
    @SerializedName("number_of_chapters") val numberOfChapters: Int,
    val status: String,
    val year: String,
    @SerializedName("image_url") val imageUrl: String
)
