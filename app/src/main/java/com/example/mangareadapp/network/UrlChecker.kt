package com.example.mangareadapp.network

import okhttp3.*
import java.io.IOException

class UrlChecker(private val client: OkHttpClient) {

    fun checkAndGetFinalUrl(url: String, callback: (String) -> Unit) {
        val request = Request.Builder()
            .url(url)
            .head()  // Используем HEAD запрос для проверки доступности
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                // Если запрос не удался, пробуем альтернативный домен
                val newUrl = url.replace("mangapoisk.live", "m3.yaoipoisk.net")
                callback(newUrl) // Передаем новый URL в callback
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    // Если запрос успешен, используем исходный URL
                    callback(url)
                } else {
                    // Если запрос не успешен, пробуем альтернативный домен
                    val newUrl = url.replace("mangapoisk.live", "m3.yaoipoisk.net")
                    callback(newUrl)
                }
            }
        })
    }
}
