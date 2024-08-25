package com.example.mangareadapp.network

import android.util.Log
import okhttp3.*
import java.io.IOException

//TODO: ЭТО ВСЁ НУЖНО СДЕЛАТЬ КАК ЧЕЛОВЕК А ТЫ ЕБЛАН ТУПОЙ НАХУЙ 
class UrlChecker(private val client: OkHttpClient) {

    fun checkAndGetFinalUrl(url: String, endpoint: String, callback: (String) -> Unit) {

        val finalurl = "${url}?tab=chapters"
        val fullUrl = "$endpoint$finalurl" // Формируем полный URL для запроса
        Log.d("fullUrl", fullUrl)
        val request = Request.Builder()
            .url(fullUrl)
            .get()  // GET запрос для получения содержимого
//            .addHeader("Cache-Control", "no-cache") // Добавляем заголовок
            .build()

        val newUrl = url.replace("mangapoisk.live", "m3.yaoipoisk.net")

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.d("Мы в onFailure", "Ошибка запроса: ${e.message}")
                callback(newUrl) // Передаем новый URL в callback при ошибке
            }

            override fun onResponse(call: Call, response: Response) {
                val responseBody = response.body?.string() ?: "null"
                Log.d("Ответ", responseBody)

                // Проверка на наличие слова "title" в теле ответа
                if (responseBody.contains("title", ignoreCase = true)) {
                    Log.d("Успех", "Ответ содержит слово 'title'")
                    callback(url)  // Продолжить с обычным URL
                } else {
                    Log.d("Ошибка", "Получен пустой ответ или слово 'title' отсутствует")
                    callback(newUrl)  // Используем альтернативный URL
                }
            }
        })
    }
}
