package com.example.project.ui

import androidx.compose.runtime.mutableStateListOf
import com.example.project.data.database.posts.Post

object DataManager {
    // Use a MutableStateList for posts that is
    var posts: List<Post> = listOf()
}