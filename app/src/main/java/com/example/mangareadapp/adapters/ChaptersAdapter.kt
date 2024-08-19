package com.example.mangareadapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mangareadapp.api.Chapter

class ChapterAdapter : RecyclerView.Adapter<ChapterAdapter.ChapterViewHolder>() {

    private var chapters: List<Chapter> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChapterViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_chapter, parent, false)
        return ChapterViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChapterViewHolder, position: Int) {
        val chapter = chapters[position]
        holder.bind(chapter)
    }

    override fun getItemCount(): Int = chapters.size

    fun submitList(chapters: List<Chapter>) {
        this.chapters = chapters
        notifyDataSetChanged()
    }

    class ChapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.textViewChapterTitle)
        private val dateTextView: TextView = itemView.findViewById(R.id.textViewChapterDate)

        fun bind(chapter: Chapter) {
            titleTextView.text = chapter.title
            dateTextView.text = chapter.date
        }
    }
}
