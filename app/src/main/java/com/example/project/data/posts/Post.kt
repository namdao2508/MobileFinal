package com.example.project.data.posts

import com.example.project.data.songs.Song
import kotlinx.serialization.Serializable

@Serializable
data class Post(
    val title: String,
    val body: String,
    val song: Song
)
