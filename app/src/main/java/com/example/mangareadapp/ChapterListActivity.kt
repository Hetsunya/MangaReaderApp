package com.example.mangareadapp

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mangareadapp.api.ApiService
import com.example.mangareadapp.api.Chapter
import com.example.mangareadapp.network.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChapterListActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var chapterAdapter: ChapterAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chapter_list)

        recyclerView = findViewById(R.id.recyclerViewChapters)
        recyclerView.layoutManager = LinearLayoutManager(this)
        chapterAdapter = ChapterAdapter()
        recyclerView.adapter = chapterAdapter

        val mangaUrl = intent.getStringExtra("mangaUrl")
        if (mangaUrl != null) {
            fetchChapters(mangaUrl)
        } else {
            Toast.makeText(this, "URL манги не найден", Toast.LENGTH_SHORT).show()
        }
    }

    private fun fetchChapters(mangaUrl: String) {
        val apiService = RetrofitInstance.retrofit.create(ApiService::class.java)
        val call = apiService.getChapters(mangaUrl)

        call.enqueue(object : Callback<List<Chapter>> {
            override fun onResponse(call: Call<List<Chapter>>, response: Response<List<Chapter>>) {
                if (response.isSuccessful) {
                    val chapters = response.body() ?: emptyList()
                    Log.d("ChapterListActivity", "Chapters: $chapters")
                    chapterAdapter.submitList(chapters)
                } else {
                    Toast.makeText(this@ChapterListActivity, "Ошибка: ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Chapter>>, t: Throwable) {
                Toast.makeText(this@ChapterListActivity, "Ошибка: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
