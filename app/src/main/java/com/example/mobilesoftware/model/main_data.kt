package com.example.mobilesoftware.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

class main_data {
    @Parcelize
    data class Post(
        val title:String,
        val imageUrl: String
    ) : Parcelable

    data class Comment(val author: String, val content: String)
}