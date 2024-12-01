package com.example.project.ui.screen.ai

import android.content.Context
import androidx.compose.ui.graphics.colorspace.Connector
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.project.network.SpotifyApiService
import com.google.ai.client.generativeai.GenerativeModel
import com.google.android.things.bluetooth.ConnectionParams
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class GenAIViewModel @Inject constructor(
    private val generativeModel: GenerativeModel, // Injected Generative AI model
    private val spotifyApiService: SpotifyApiService, // Injected Spotify API Service
    private val context: Context // Injected Context for Spotify App Remote
) : ViewModel() {

    private val _textGenerationResult = MutableStateFlow<String?>(null)
    val textGenerationResult = _textGenerationResult.asStateFlow()

//    private val _spotifyConnectionStatus = MutableStateFlow("Disconnected")
//    val spotifyConnectionStatus = _spotifyConnectionStatus.asStateFlow()
//
//    private var spotifyAppRemote: SpotifyAppRemote? = null
//
//    private val CLIENT_ID = "YOUR_SPOTIFY_CLIENT_ID"
//    private val REDIRECT_URI = "YOUR_APP_REDIRECT_URI"
//
//    // Connect to Spotify
//    fun connectToSpotify() {
//        val connectionParams = ConnectionParams.Builder(CLIENT_ID)
//            .setRedirectUri(REDIRECT_URI)
//            .showAuthView(true)
//            .build()
//
//        SpotifyAppRemote.connect(context, connectionParams, object : Connector.ConnectionListener {
//            override fun onConnected(spotifyAppRemote: SpotifyAppRemote) {
//                this@GenAIViewModel.spotifyAppRemote = spotifyAppRemote
//                _spotifyConnectionStatus.value = "Connected to Spotify"
//            }
//
//            override fun onFailure(throwable: Throwable) {
//                _spotifyConnectionStatus.value = "Failed to connect: ${throwable.message}"
//            }
//        })
//    }
//
//    // Disconnect from Spotify
//    fun disconnectFromSpotify() {
//        spotifyAppRemote?.let {
//            SpotifyAppRemote.disconnect(it)
//            spotifyAppRemote = null
//            _spotifyConnectionStatus.value = "Disconnected"
//        }
//    }
//
//    // Play a song using Spotify
//    fun playSong(songUri: String) {
//        spotifyAppRemote?.playerApi?.play(songUri)?.setResultCallback {
//            _textGenerationResult.value = "Playing song..."
//        }?.setErrorCallback {
//            _textGenerationResult.value = "Error playing song: ${it.message}"
//        } ?: run {
//            _textGenerationResult.value = "Spotify not connected"
//        }
//    }

    // Generate AI Recommendations
    fun generateAI(mood: String) {
        _textGenerationResult.value = "Generating recommendations..."
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val prompt = "I am in a $mood mood. Suggest 10 songs that match this mood."
                val result = generativeModel.generateContent(prompt)
                _textGenerationResult.value = result.text
            } catch (e: Exception) {
                _textGenerationResult.value = "Error: ${e.message}"
            }
        }
    }

    // Search Songs Using Spotify API
    fun searchSongs(query: String, accessToken: String) {
        _textGenerationResult.value = "Searching for songs..."
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = spotifyApiService.searchSongs(query, token = "Bearer $accessToken")
                if (response.isSuccessful) {
                    val songs = response.body()?.tracks?.items.orEmpty()
                    _textGenerationResult.value = songs.joinToString("\n") { song ->
                        "${song.name} by ${song.artists.joinToString { it.name }}"
                    }
                } else {
                    _textGenerationResult.value = "Error: ${response.errorBody()?.string()}"
                }
            } catch (e: Exception) {
                _textGenerationResult.value = "Error: ${e.message}"
            }
        }
    }

    // Clear Text Results
    fun clearTextResults() {
        _textGenerationResult.value = null
    }
}
