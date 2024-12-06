package com.example.project.ui.screen

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.project.data.database.posts.Post
import com.example.project.ui.DataManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {
    // Use a MutableStateList for posts that is observable by Composables
    private val _posts = mutableStateListOf<Post>()
    val posts: List<Post> get() = _posts


    init {
        DataManager.posts = _posts
    }

    // Add new post
    fun addPost(post: Post) {
        _posts.add(post)
    }

    // Remove  post
    fun removePost(post: Post) {
        _posts.remove(post)
    }
}
