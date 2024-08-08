package com.example.mangareadapp

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.mangareadapp.api.Manga
import com.squareup.picasso.Picasso

class MangaDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manga_details)

        // Получаем ссылки на элементы интерфейса
        val imageViewManga: ImageView = findViewById(R.id.imageViewManga)
        val textViewMangaTitle: TextView = findViewById(R.id.textViewMangaTitle)

        // Получаем данные манги из интента
        val manga = intent.getParcelableExtra<Manga>("manga")

        // Отображаем данные, если манга не null
        manga?.let {
            textViewMangaTitle.text = it.title
            Picasso.get().load(it.imageUrl).into(imageViewManga)
        }
    }
}
