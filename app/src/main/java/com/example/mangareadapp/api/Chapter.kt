package com.example.mangareadapp.api

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class Chapter(
    val title: String,
    val date: String,
    val link: String,
    val pages: Pages
)

data class Pages(
    val image_url: String
)

