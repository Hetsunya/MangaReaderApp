package com.example.mangareadapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mangareadapp.api.Manga
import com.squareup.picasso.Picasso

class MangaAdapter(private val onClick: (Manga) -> Unit) :
    ListAdapter<Manga, MangaAdapter.MangaViewHolder>(MangaDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MangaViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_manga, parent, false)
        return MangaViewHolder(view)
    }

    override fun onBindViewHolder(holder: MangaViewHolder, position: Int) {
        val manga = getItem(position)
        holder.bind(manga, onClick)
    }

    class MangaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textViewTitle: TextView = itemView.findViewById(R.id.textViewTitle)
        private val imageViewThumbnail: ImageView = itemView.findViewById(R.id.imageViewThumbnail)

        fun bind(manga: Manga, onClick: (Manga) -> Unit) {
            textViewTitle.text = manga.title
            Picasso.get().load(manga.imageUrl).into(imageViewThumbnail)

            itemView.setOnClickListener { onClick(manga) }
        }
    }
}

class MangaDiffCallback : DiffUtil.ItemCallback<Manga>() {
    override fun areItemsTheSame(oldItem: Manga, newItem: Manga): Boolean {
        return oldItem.url == newItem.url
    }

    override fun areContentsTheSame(oldItem: Manga, newItem: Manga): Boolean {
        return oldItem == newItem
    }
}
