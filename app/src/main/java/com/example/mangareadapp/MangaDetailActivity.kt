package com.example.mangareadapp

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import com.example.mangareadapp.api.Chapter
import com.example.mangareadapp.api.Manga
import android.widget.Button
import android.widget.LinearLayout
import com.example.mangareadapp.network.RetrofitInstance
import android.util.Log
import android.widget.Toast

class MangaDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manga_detail)

        val imageView = findViewById<ImageView>(R.id.imageView)
        val titleView = findViewById<TextView>(R.id.titleView)
        val genreView = findViewById<TextView>(R.id.genreView)
        val descriptionView = findViewById<TextView>(R.id.descriptionView)
        val chaptersButton = findViewById<Button>(R.id.chaptersButton)
        val chaptersList = findViewById<LinearLayout>(R.id.chaptersList)

        // Получение данных из интента
        val manga = intent.getParcelableExtra<Manga>("manga")
        val genres = intent.getStringExtra("genres").toString() ?: ""
        val description = intent.getStringExtra("description").toString() ?: ""

        // Установка данных в UI
        Picasso.get().load(manga?.imageUrl).into(imageView)
        titleView.text = manga?.title
        genreView.text = genres
        descriptionView.text = description
        Log.d("MangaDetailActivity", "Manga imageUrl: ${manga?.imageUrl}")
        Log.d("MangaDetailActivity", "Manga title: ${manga?.title}")
        Log.d("MangaDetailActivity", "Genres: $genres")
        Log.d("MangaDetailActivity", "Description: $description")

        // Обработчик нажатия на кнопку "View Chapters"
//        chaptersButton.setOnClickListener {
//            fetchChapters(manga?.url ?: "", chaptersList)
//        }
    }

//    ПОТОМ ДЕЛАТЬ НАДА ЫЫЫЫЫЫЫЫЫЫЫЫ
//    private fun fetchChapters(mangaUrl: String, chaptersList: LinearLayout) {
//        // Здесь делается запрос для получения списка глав
//        val apiService = RetrofitInstance.apiService
//        val call = apiService.getChapters(mangaUrl)
//
//        call.enqueue(object : Callback<List<Chapter>> {
//            override fun onResponse(call: Call<List<Chapter>>, response: Response<List<Chapter>>) {
//                if (response.isSuccessful) {
//                    val chapters = response.body() ?: emptyList()
//                    displayChapters(chapters, chaptersList)
//                }
//            }
//
//            override fun onFailure(call: Call<List<Chapter>>, t: Throwable) {
//                // Обработка ошибки
//            }
//        })
//    }

    private fun displayChapters(chapters: List<Chapter>, chaptersList: LinearLayout) {
        chaptersList.removeAllViews()
        for (chapter in chapters) {
            val button = Button(this)
            button.text = "${chapter.title} - ${chapter.date}"
            button.setOnClickListener {
                // Заглушка для обработки нажатия
                Toast.makeText(this, "Clicked on ${chapter.title}", Toast.LENGTH_SHORT).show()
            }
            chaptersList.addView(button)
        }
    }
}
