package com.example.mangareadapp.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import com.example.mangareadapp.api.MangaResponse

interface ApiService {
    @GET("/search")
    fun searchManga(@Query("q") query: String): Call<MangaResponse>

    @GET("mangaDetails")
    suspend fun getMangaDetails(@Query("url") url: String): Manga

    @GET("/scrap/chapters")
    fun getChapters(@Query("url") mangaUrl: String): Call<List<Chapter>>
}
