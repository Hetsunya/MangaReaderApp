package com.example.mangareadapp

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mangareadapp.activities.MangaDetailActivity
import com.squareup.picasso.Picasso
import com.example.mangareadapp.models.Manga

class MangaAdapter(private val mangas: List<Manga>) : RecyclerView.Adapter<MangaAdapter.MangaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MangaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_manga, parent, false)
        return MangaViewHolder(view)
    }

    override fun onBindViewHolder(holder: MangaViewHolder, position: Int) {
        val manga = mangas[position]
        holder.bind(manga)
    }

    override fun getItemCount() = mangas.size

    class MangaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.imageView)
        private val titleView: TextView = itemView.findViewById(R.id.titleView)

        fun bind(manga: Manga) {
            titleView.text = manga.title
            if (manga.imageUrl.isNotEmpty()) {
                Picasso.get().load(manga.imageUrl).into(imageView)
            }

            // Устанавливаем OnClickListener для перехода к деталям манги
            itemView.setOnClickListener {
                val context = itemView.context
                val intent = Intent(context, MangaDetailActivity::class.java).apply {
                    putExtra("title", manga.title)
                    putExtra("imageUrl", manga.imageUrl)
                    putExtra("url", manga.url)
                    putExtra("manga", manga)
                }
                intent.putExtra("manga_url", manga.url)
                context.startActivity(intent)

                context.startActivity(intent)
            }
        }
    }
}
