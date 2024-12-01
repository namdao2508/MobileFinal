package com.example.project.data.songs

import kotlinx.serialization.SerialName

data class Album(
    @SerialName("name") val name: String
)