package com.example.mangareadapp

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso

class MangaDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manga_detail)

        // Получаем данные из Intent
        val title = intent.getStringExtra("title")
        val imageUrl = intent.getStringExtra("imageUrl")
        val url = intent.getStringExtra("url")

        // Настраиваем виджеты
        val imageView = findViewById<ImageView>(R.id.imageView)
        val titleView = findViewById<TextView>(R.id.titleView)
        val urlView = findViewById<TextView>(R.id.urlView)

        // Устанавливаем данные
        titleView.text = title
        urlView.text = url
        Picasso.get().load(imageUrl).into(imageView)
    }
}
