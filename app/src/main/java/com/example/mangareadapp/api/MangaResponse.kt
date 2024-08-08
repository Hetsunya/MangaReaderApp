package com.example.mangareadapp.api

import com.google.gson.annotations.SerializedName

data class MangaResponse(
    @SerializedName("found_mangas") val foundMangas: List<Manga>
)
