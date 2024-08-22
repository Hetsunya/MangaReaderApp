package com.example.mangareadapp.activities

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.mangareadapp.R
import com.example.mangareadapp.adapters.MangaReaderAdapter

class MangaReaderActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manga_reader)

        val viewPager = findViewById<ViewPager2>(R.id.viewPager)
        val progressTextView = findViewById<TextView>(R.id.progressTextView)

        val imageUrls = intent.getStringArrayListExtra("image_urls") ?: arrayListOf()

        // Устанавливаем адаптер для ViewPager2
        val adapter = MangaReaderAdapter(this, imageUrls)
        viewPager.adapter = adapter

        // Обновляем прогресс
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                progressTextView.text = "${position + 1}/${adapter.itemCount}"
            }
        })

        // Устанавливаем начальную страницу
        viewPager.setCurrentItem(0, false)
    }
}
