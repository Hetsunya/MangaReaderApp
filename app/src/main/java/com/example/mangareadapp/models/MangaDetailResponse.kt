package com.example.mangareadapp.models

import com.google.gson.annotations.SerializedName

data class MangaDetailResponse(
    @SerializedName("title") val titles: List<String>,
    @SerializedName("description") val description: String,
    @SerializedName("genre") val genres: List<String>,
    @SerializedName("chapters") val chapters: List<Chapter>?,  // Если chapters могут быть null
    @SerializedName("number_of_chapters") val numberOfChapters: String,
    @SerializedName("status") val status: String,
    @SerializedName("year") val year: String,
    @SerializedName("image_url") val imageUrl: String
)
