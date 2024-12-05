package com.example.project.ui.screen.ai

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.project.network.SpotifyApiService
import com.google.ai.client.generativeai.GenerativeModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GenAIViewModel @Inject constructor(
    val spotifyApiService: SpotifyApiService,
    @ApplicationContext val context: Context
) : ViewModel() {


    private val generativeModel = GenerativeModel(
        modelName = "gemini-pro",
        apiKey = "AIzaSyDH4TYDouCQxBXuk9ua3jHPlKwLYg0sNmg"
    )

    private val _textGenerationResult = MutableStateFlow<String?>(null)
    val textGenerationResult = _textGenerationResult.asStateFlow()

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
            /*try {
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
            }*/
        }
    }

    // Clear Text Results
    fun clearTextResults() {
        _textGenerationResult.value = null
    }
}