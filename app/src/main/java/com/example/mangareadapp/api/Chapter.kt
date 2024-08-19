package com.example.mangareadapp.api

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Chapter(
    val title: String,
    val date: String,
    val link: String
) : Parcelable
