package com.example.project.ui.data.songs

import kotlinx.serialization.SerialName

data class Tracks(
    @SerialName("items") val items: List<Song>
)