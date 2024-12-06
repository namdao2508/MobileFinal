package com.example.project.ui.screen.post

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.project.data.posts.Post
import com.example.project.data.posts.PostDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.setValue
//import androidx.lifecycle.ViewModel
//import com.example.project.data.database.posts.Post
//import com.example.project.data.database.posts.PostWithId
//import com.google.firebase.firestore.FirebaseFirestore
//import kotlinx.coroutines.channels.awaitClose
//import kotlinx.coroutines.flow.callbackFlow
//
//class PostViewModel : ViewModel() {
//    var postUiState: PostUIState by mutableStateOf(PostUIState.Init)
//
//    fun postsList() = callbackFlow {
//        val snapshotListener =
//            FirebaseFirestore.getInstance().collection("posts")
//                .addSnapshotListener() { snapshot, e ->
//                    val response = if (snapshot != null) {
//                        val postList = snapshot.toObjects(Post::class.java)
//                        val postWithIdList = mutableListOf<PostWithId>()
//
//                        postList.forEachIndexed { index, post ->
//                            postWithIdList.add(PostWithId(snapshot.documents[index].id, post))
//                        }
//
//                        PostUIState.Success(
//                            postWithIdList
//                        )
//                    } else {
//                        PostUIState.Error(e?.message.toString())
//                    }
//
//                    trySend(response) // emit this value through the flow
//                }
//        awaitClose { // when we navigate out from the screen,
//            // the flow stops and we stop here the firebase listener
//            snapshotListener.remove()
//        }
//    }
//
//    fun deletePost(postKey: String) {
//        FirebaseFirestore.getInstance().collection(
//            "posts"
//        ).document(postKey).delete()
//    }
//
//}
//
//
//
//sealed interface PostUIState {
//    object Init : PostUIState
//    object Loading : PostUIState
//    data class Success(val postList: List<PostWithId>) : PostUIState
//    data class Error(val error: String?) : PostUIState
//}

@HiltViewModel
class PostViewModel @Inject constructor(
    private val PostDAO: PostDao
) : ViewModel() {


    fun getAllPosts(): Flow<List<Post>> {
        return PostDAO.getAll()
    }

    fun addPst(post: Post) {
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

//    fun toggleItemBoughtStatus(item: ShoppingItem, isBought: Boolean) {
//        viewModelScope.launch(Dispatchers.IO) {
//            val updatedItem = item.copy(isBought = isBought)
//            shoppingDAO.update(updatedItem)
//        }
//    }

    fun clearAllItems() {
        viewModelScope.launch(Dispatchers.IO) {
            PostDAO.deleteAll()
        }
    }
}