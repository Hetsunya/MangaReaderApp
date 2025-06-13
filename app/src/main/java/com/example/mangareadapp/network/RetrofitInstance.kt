package com.example.mangareadapp.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client = OkHttpClient.Builder()
        .connectTimeout(4, java.util.concurrent.TimeUnit.SECONDS) // Тайм-аут на подключение
        .readTimeout(4, java.util.concurrent.TimeUnit.SECONDS)    // Тайм-аут на чтение данных
        .writeTimeout(4, java.util.concurrent.TimeUnit.SECONDS)   // Тайм-аут на запись данных
        .retryOnConnectionFailure(false)
        .addInterceptor(loggingInterceptor)
        .build()

    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/") // Адрес для эмулятора
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val apiService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}
