package com.example.mangareadapp.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import android.widget.TextView
import com.example.mangareadapp.R
import com.example.mangareadapp.adapters.MangaReaderAdapter

class MangaReaderActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager2
    private lateinit var progressTextView: TextView
    private lateinit var mangaReaderAdapter: MangaReaderAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manga_reader)

        // Получаем данные главы (список ссылок на картинки)
        val imageUrls = intent.getStringArrayListExtra("imageUrls") ?: arrayListOf()

        // Инициализация ViewPager2 и адаптера
        viewPager = findViewById(R.id.viewPager)
        progressTextView = findViewById(R.id.progressTextView)
        mangaReaderAdapter = MangaReaderAdapter(this, imageUrls)
        viewPager.adapter = mangaReaderAdapter

        // Установка слушателя на изменение страницы для обновления прогресса
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                updateProgress(position + 1, imageUrls.size)
            }
        })

        // Установка начального прогресса
        updateProgress(1, imageUrls.size)
    }

    // Метод для обновления текста прогресса
    private fun updateProgress(current: Int, total: Int) {
        progressTextView.text = getString(R.string.progress_text, current, total)
    }
}
