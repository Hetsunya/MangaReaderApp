package com.example.mangareadapp.activities

import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.mangareadapp.R
import com.example.mangareadapp.adapters.MangaReaderAdapter

class MangaReaderActivity : AppCompatActivity() {

    private var isVerticalScroll = true
    private lateinit var viewPager: ViewPager2
    private lateinit var settingsButton: ImageView
    private lateinit var settingsContainer: LinearLayout
    private lateinit var verticalScrollButton: Button
    private lateinit var horizontalScrollButton: Button
    private lateinit var touchArea: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manga_reader)

        viewPager = findViewById(R.id.viewPager)
        settingsButton = findViewById(R.id.settingsButton)
        settingsContainer = findViewById(R.id.settingsContainer)
        verticalScrollButton = findViewById(R.id.verticalScrollButton)
        horizontalScrollButton = findViewById(R.id.horizontalScrollButton)
        touchArea = findViewById(R.id.touchArea)

        val imageUrls = intent.getStringArrayListExtra("image_urls") ?: arrayListOf()
        val adapter = MangaReaderAdapter(this, imageUrls)
        viewPager.adapter = adapter

        // Устанавливаем начальное направление прокрутки
        updateScrollDirection()

        // Показ и скрытие кнопок настроек
        touchArea.setOnClickListener {
            if (settingsButton.visibility == View.VISIBLE) {
                settingsButton.visibility = View.GONE
                settingsContainer.visibility = View.GONE
            } else {
                settingsButton.visibility = View.VISIBLE
                settingsContainer.visibility = View.VISIBLE
            }
        }

        // Обработчики нажатия на кнопки настроек
        verticalScrollButton.setOnClickListener {
            isVerticalScroll = true
            updateScrollDirection()
            settingsButton.visibility = View.GONE
            settingsContainer.visibility = View.GONE
        }

        horizontalScrollButton.setOnClickListener {
            isVerticalScroll = false
            updateScrollDirection()
            settingsButton.visibility = View.GONE
            settingsContainer.visibility = View.GONE
        }
    }

    private fun updateScrollDirection() {
        viewPager.orientation = if (isVerticalScroll) {
            ViewPager2.ORIENTATION_VERTICAL
        } else {
            ViewPager2.ORIENTATION_HORIZONTAL
        }
    }
}
