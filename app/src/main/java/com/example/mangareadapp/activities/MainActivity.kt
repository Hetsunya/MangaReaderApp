package com.example.mangareadapp.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.mangareadapp.R
import com.example.mangareadapp.network.ApiService
import com.example.mangareadapp.models.MangaResponse
import com.example.mangareadapp.network.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var editTextQuery: EditText
    private lateinit var buttonSearch: Button
    private lateinit var resultLayout: LinearLayout
    private lateinit var toolbar: Toolbar
    private lateinit var buttonSearchTop: Button
    private lateinit var textViewTitle: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Инициализация элементов
        editTextQuery = findViewById(R.id.editTextQuery)
        buttonSearch = findViewById(R.id.buttonSearch)
        resultLayout = findViewById(R.id.resultLayout)
        toolbar = findViewById(R.id.toolbar)
        buttonSearchTop = findViewById(R.id.buttonSearchTop)
        textViewTitle = findViewById(R.id.textViewTitle)

        // Устанавливаем Toolbar как ActionBar
        setSupportActionBar(toolbar)

        buttonSearchTop.setOnClickListener {
            if (editTextQuery.visibility == View.GONE) {
                // Показываем поле поиска и кнопку поиска
                editTextQuery.visibility = View.VISIBLE
                buttonSearch.visibility = View.VISIBLE
                textViewTitle.visibility = View.GONE
                buttonSearchTop.text = "✅" // Изменяем текст на кнопке, чтобы показать подтверждение
            } else {
                // Скрываем поле поиска и кнопку поиска
                editTextQuery.visibility = View.GONE
                buttonSearch.visibility = View.GONE
                textViewTitle.visibility = View.VISIBLE
                buttonSearchTop.text = "🔍" // Возвращаем текст кнопки к исходному состоянию

                val query = editTextQuery.text.toString()
                if (query.isNotEmpty()) {
                    searchManga(query)
                }
            }
        }

        buttonSearch.setOnClickListener {
            val query = editTextQuery.text.toString()
            if (query.isNotEmpty()) {
                searchManga(query)
            }
        }
    }

    private fun searchManga(query: String) {
        val apiService = RetrofitInstance.retrofit.create(ApiService::class.java)
        val call = apiService.searchManga(query)

        call.enqueue(object : Callback<MangaResponse> {
            override fun onResponse(call: Call<MangaResponse>, response: Response<MangaResponse>) {
                if (response.isSuccessful) {
                    val mangas = response.body()?.foundMangas ?: emptyList()
                    Log.d("MainActivity", "Displaying mangas: $mangas")
                    for (manga in mangas) {
                        Log.d("MainActivity", "Manga: url=${manga.url}, title=${manga.title}, imageUrl=${manga.imageUrl}")
                    }
                    val intent = Intent(this@MainActivity, SearchResultsActivity::class.java)
                    intent.putParcelableArrayListExtra("mangas", ArrayList(mangas))
                    startActivity(intent)
                } else {
                    displayError("Error: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<MangaResponse>, t: Throwable) {
                displayError("Failure: ${t.message}")
            }
        })
    }

    private fun displayError(message: String) {
        resultLayout.removeAllViews()
        val textView = TextView(this)
        textView.text = message
        resultLayout.addView(textView)
    }
}
