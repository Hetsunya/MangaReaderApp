package com.example.mangareadapp.activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mangareadapp.MangaAdapter
import com.example.mangareadapp.R
import com.example.mangareadapp.models.Manga

class SearchResultsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_results)

        val mangas = intent.getParcelableArrayListExtra<Manga>("mangas") ?: emptyList<Manga>()
        Log.d("SearchResultsActivity", "Received mangas: $mangas")

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        recyclerView.adapter = MangaAdapter(mangas)
    }
}
