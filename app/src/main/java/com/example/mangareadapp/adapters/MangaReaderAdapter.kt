package com.example.mangareadapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mangareadapp.R

class MangaReaderAdapter(
    private val context: Context,
    private val imageUrls: List<String>
) : RecyclerView.Adapter<MangaReaderAdapter.MangaPageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MangaPageViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_manga_page, parent, false)
        return MangaPageViewHolder(view)
    }

    override fun onBindViewHolder(holder: MangaPageViewHolder, position: Int) {
        val imageUrl = imageUrls[position]
        Glide.with(context).load(imageUrl).into(holder.imageView)
    }

    override fun getItemCount(): Int {
        return imageUrls.size
    }

    class MangaPageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
    }
}
