package com.example.mangareadapp.network

import com.example.mangareadapp.models.Chapter
import com.example.mangareadapp.models.MangaDetailResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import com.example.mangareadapp.models.MangaResponse

interface ApiService {
    @GET("/search")
    fun searchManga(@Query("q") query: String): Call<MangaResponse>

    @GET("scrap")
    fun getMangaDetails(@Query("url") mangaUrl: String): Call<MangaDetailResponse>

    @GET("/scrap/chapters")
    fun getChapters(@Query("url") url : String): Call<List<Chapter>>

    @GET("/extract/images")
    fun getImages(@Query("url") url: String): Call<List<String>>
}
