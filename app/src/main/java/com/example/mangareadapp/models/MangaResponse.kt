package com.example.mangareadapp.models

import com.google.gson.annotations.SerializedName

data class MangaResponse(
    @SerializedName("found_mangas") val foundMangas: List<Manga>
)
