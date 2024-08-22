package com.example.mangareadapp.models

data class Chapter(
    val title: String,
    val date: String,
    val link: String,
    val pages: Pages
)

data class Pages(
    val image_url: String
)

