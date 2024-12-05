package com.example.project.data.songs;

data class Post(
        val title: String,
        val body: String,
        val song: Song? = null, // Reference to the associated song
)
