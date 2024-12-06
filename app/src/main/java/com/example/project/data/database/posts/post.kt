package com.example.project.data.database.posts

import com.example.project.data.songs.Song

data class Post(
    val title: String,
    val body: String,
    val song: Song
)