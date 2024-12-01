package com.example.project.data.songs

import kotlinx.serialization.SerialName

data class Song(
    @SerialName("name") val name: String,
    @SerialName("artists") val artists: List<Artist>,
    @SerialName("album") val album: Album,
    @SerialName("uri") val uri: String
)