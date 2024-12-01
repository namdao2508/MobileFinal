package com.example.project.data.songs

import kotlinx.serialization.SerialName

data class Tracks(
    @SerialName("items") val items: List<Song>
)