package com.example.mangareadapp.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("search")
    fun searchManga(@Query("q") query: String): Call<MangaResponse>
}
