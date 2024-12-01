package com.example.project.ui.data.songs

import kotlinx.serialization.SerialName

data class SongsResponse(
    @SerialName("tracks") val tracks: Tracks
)