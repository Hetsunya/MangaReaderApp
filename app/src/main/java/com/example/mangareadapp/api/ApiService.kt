package com.example.mangareadapp.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import com.example.mangareadapp.api.MangaResponse

interface ApiService {
    @GET("/search")
    fun searchManga(@Query("q") query: String): Call<MangaResponse>

    @GET("scrap")
    fun getMangaDetails(@Query("url") mangaUrl: String): Call<MangaDetailResponse>

    @GET("/scrap/chapters")
    fun getChapters(@Query("url") url: String): Call<List<Chapter>>
}
