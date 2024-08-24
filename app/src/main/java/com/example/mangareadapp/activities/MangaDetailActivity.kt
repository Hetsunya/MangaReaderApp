package com.example.mangareadapp.activities

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import com.example.mangareadapp.models.Chapter
import com.example.mangareadapp.models.MangaDetailResponse
import android.widget.Button
import android.widget.LinearLayout
import com.example.mangareadapp.network.RetrofitInstance
import android.widget.Toast
import android.net.Uri
import android.util.Log
import com.example.mangareadapp.R
import com.example.mangareadapp.network.UrlChecker
import okhttp3.OkHttpClient
import com.example.mangareadapp.models.ImageResponse

import com.example.mangareadapp.network.ApiService


class MangaDetailActivity : AppCompatActivity() {

    private lateinit var apiService: ApiService
    private lateinit var urlChecker: UrlChecker

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manga_detail)

        val imageView = findViewById<ImageView>(R.id.imageView)
        val titleView = findViewById<TextView>(R.id.titleView)
        val genreView = findViewById<TextView>(R.id.genreView)
        val descriptionView = findViewById<TextView>(R.id.descriptionView)
        val statusView = findViewById<TextView>(R.id.statusView)
        val yearView = findViewById<TextView>(R.id.yearView)
        val numberOfChaptersView = findViewById<TextView>(R.id.numberOfChaptersView)
        val chaptersButton = findViewById<Button>(R.id.chaptersButton)
        val chaptersList = findViewById<LinearLayout>(R.id.chaptersList)

        val mangaUrl = intent.getStringExtra("manga_url") ?: ""

        // Инициализация RetrofitInstance и UrlChecker
        val apiService = RetrofitInstance.apiService
        val urlChecker = UrlChecker(OkHttpClient())
        Log.d("finalManga", mangaUrl)

        // Загрузка деталей манги без проверки URL (здесь она не нужна)
        fetchMangaDetails(mangaUrl, imageView, titleView, genreView, descriptionView, statusView, yearView, numberOfChaptersView)

        // Обработчик нажатия на кнопку "View Chapters"
        chaptersButton.setOnClickListener {
            Log.d("finalManga в клике до", mangaUrl)

            // Проверка и получение правильного URL перед запросом глав
            urlChecker.checkAndGetFinalUrl(mangaUrl, "http://10.0.2.2:8080/scrap/chapters?url=") { finalUrl ->
                Log.d("finalManga в клике после", finalUrl)
                fetchChapters(finalUrl, chaptersList)  // Используем проверенный URL для запроса глав
            }
        }
    }


    private fun fetchMangaDetails(mangaUrl: String, imageView: ImageView, titleView: TextView,
                                  genreView: TextView, descriptionView: TextView, statusView: TextView,
                                  yearView: TextView, numberOfChaptersView: TextView) {
        val apiService = RetrofitInstance.apiService
        val call = apiService.getMangaDetails(mangaUrl)

        call.enqueue(object : Callback<MangaDetailResponse> {
            override fun onResponse(call: Call<MangaDetailResponse>, response: Response<MangaDetailResponse>) {
                if (response.isSuccessful) {
                    val mangaDetail = response.body()
                    if (mangaDetail != null) {
                        Picasso.get().load(mangaDetail.imageUrl).into(imageView)
                        titleView.text = mangaDetail.titles[0]
                        genreView.text = mangaDetail.genres.joinToString(separator = ", ")
                        statusView.text = mangaDetail.status
                        yearView.text = mangaDetail.year
                        numberOfChaptersView.text = mangaDetail.numberOfChapters
                        descriptionView.text = mangaDetail.description
                    }
                }
            }

            override fun onFailure(call: Call<MangaDetailResponse>, t: Throwable) {
                Toast.makeText(this@MangaDetailActivity, "Ошибка загрузки данных", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun fetchChapters(mangaUrl: String, chaptersList: LinearLayout) {
        val apiService = RetrofitInstance.apiService

        // Парсим URL и добавляем параметр
        val parsedUri = Uri.parse(mangaUrl)
        val chaptersUri = parsedUri.buildUpon()
            .appendQueryParameter("tab", "chapters")
            .build()

        val chaptersUrl = chaptersUri.toString()

        val call = apiService.getChapters(chaptersUrl)

        call.enqueue(object : Callback<List<Chapter>> {
            override fun onResponse(call: Call<List<Chapter>>, response: Response<List<Chapter>>) {
                if (response.isSuccessful) {
                    val chapters = response.body() ?: emptyList()
                    displayChapters(chapters, chaptersList)
                } else {
                    Toast.makeText(this@MangaDetailActivity, "Ошибка загрузки глав", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Chapter>>, t: Throwable) {
                Toast.makeText(this@MangaDetailActivity, "Ошибка загрузки глав", Toast.LENGTH_SHORT).show()
            }
        })
    }


    private fun displayChapters(chapters: List<Chapter>, chaptersList: LinearLayout) {
        chaptersList.removeAllViews()
        for (chapter in chapters) {
            val button = Button(this)
            button.text = "${chapter.title} - ${chapter.date}"
            button.setOnClickListener {
                fetchChapterImages(chapter.link)
            }
            chaptersList.addView(button)
        }
    }

    private fun fetchChapterImages(chapterUrl: String) {
        val apiService = RetrofitInstance.apiService

        val fullUrl = "https://mangapoisk.live$chapterUrl"

        val call = apiService.getImages(fullUrl)

        call.enqueue(object : Callback<List<ImageResponse>> {
            override fun onResponse(call: Call<List<ImageResponse>>, response: Response<List<ImageResponse>>) {
                if (response.isSuccessful) {
                    val images = response.body() ?: emptyList()
                    Log.d("images", images.toString())
                    if (images.isNotEmpty()) {
                        val imageUrls = images.map { it.image_url }
                        // Переход к активности для отображения изображений
                        val intent = Intent(this@MangaDetailActivity, MangaReaderActivity::class.java)
                        intent.putStringArrayListExtra("image_urls", ArrayList(imageUrls))
                        startActivity(intent)
                    } else {
                        Toast.makeText(this@MangaDetailActivity, "Нет изображений для этой главы", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@MangaDetailActivity, "Ошибка загрузки изображений", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<ImageResponse>>, t: Throwable) {
                Toast.makeText(this@MangaDetailActivity, "Ошибка загрузки изображений", Toast.LENGTH_SHORT).show()
            }
        })
    }

}
