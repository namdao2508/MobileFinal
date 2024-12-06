package com.example.project.data.songs

import kotlinx.serialization.Serializable

@Serializable
data class Song(val title: String, val artist: String)
