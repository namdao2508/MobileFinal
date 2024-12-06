package com.example.project.ui.screen

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.project.data.database.posts.Post

class MainViewModel : ViewModel() {
    // Use a MutableStateList for posts that is observable by Composables
    private val _posts = mutableStateListOf<Post>()
    val posts: List<Post> get() = _posts

    // Add new post
    fun addPost(post: Post) {
        _posts.add(post)
    }
}
