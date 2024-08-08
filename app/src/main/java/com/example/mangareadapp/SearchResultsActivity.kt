package com.example.mangareadapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mangareadapp.api.Manga

class SearchResultsActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var mangaAdapter: MangaAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_results)

        recyclerView = findViewById(R.id.recyclerViewManga)
        recyclerView.layoutManager = GridLayoutManager(this, 2) // 2 columns
        mangaAdapter = MangaAdapter { manga -> onMangaClicked(manga) }
        recyclerView.adapter = mangaAdapter

        val foundMangas = intent.getParcelableArrayListExtra<Manga>("mangas") ?: emptyList()

        Log.d("SearchResultsActivity", "Received mangas: $foundMangas")
        displayMangas(foundMangas)
    }

    private fun displayMangas(mangas: List<Manga>) {
        Log.d("SearchResultsActivity", "Displaying mangas: $mangas")
        mangaAdapter.submitList(mangas)
    }

    private fun onMangaClicked(manga: Manga) {
        val intent = Intent(this, MangaDetailsActivity::class.java)
        intent.putExtra("manga", manga)
        startActivity(intent)
    }
}
