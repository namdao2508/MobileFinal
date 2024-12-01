package com.example.project.ui.network

import com.example.project.ui.data.songs.SongsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface SpotifyApiService {
    @GET("v1/search")
    suspend fun searchSongs(
        @Query("q") query: String,
        @Query("type") type: String = "track",
        @Header("Authorization") token: String
    ): Response<SongsResponse>
}
