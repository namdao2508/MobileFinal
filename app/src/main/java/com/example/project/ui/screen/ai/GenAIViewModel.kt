package com.example.project.ui.screen.ai

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.project.data.songs.Song
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

    fun searchSongs(query: String) {
        _textGenerationResult.value = "Searching for songs..."
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val prompt = "Search for the song '$query' and provide its details in the format: 1. Title by Artist"
                val result = generativeModel.generateContent(prompt)
                _textGenerationResult.value = result.text // Store the raw result
            } catch (e: Exception) {
                _textGenerationResult.value = "Error: ${e.message}"
            }
        }
    }

    fun parseSongs(textResult: String): List<Song> {
        return textResult.split("\n").mapNotNull { line ->
            // Split the line into parts by " by "
            val parts = line.split(" by ")
            if (parts.size == 2) {
                // Remove numbering, period, and quotes from the title
                val title = parts[0]
                    .substringAfter(".") // Remove numbering and period
                    .replace("\"", "") // Remove quotes
                    .trim() // Trim whitespace
                val artist = parts[1].trim() // Trim whitespace
                Song(title = title, artist = artist)
            } else null
        }
    }


    // Clear Text Results
    fun clearTextResults() {
        _textGenerationResult.value = null
    }
}