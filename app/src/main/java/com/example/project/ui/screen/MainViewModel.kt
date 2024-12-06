package com.example.project.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.project.data.posts.Post
import com.example.project.data.posts.PostDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    val PostDAO: PostDao
) : ViewModel() {


    fun getAllPosts(): Flow<List<Post>> {
        return PostDAO.getAll()
    }

    fun addPost(post: Post) {
        viewModelScope.launch(Dispatchers.IO) {
            PostDAO.insert(post)
        }
    }

    fun removePost(post: Post) {
        viewModelScope.launch(Dispatchers.IO) {
            PostDAO.delete(post)
        }
    }

    fun editPost(originalPost: Post, editedPost: Post) {
        viewModelScope.launch(Dispatchers.IO) {
            PostDAO.update(editedPost) // Not finished nor correct yet
        }
    }
}
