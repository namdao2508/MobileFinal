package com.example.project.data.songs

import kotlinx.serialization.SerialName

data class Artist(
    @SerialName("name") val name: String
)