package com.example.project.ui.screen.createPost

//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.setValue
//import androidx.lifecycle.ViewModel
//import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.firestore.FirebaseFirestore
//import com.example.project.data.database.posts.Post
//import com.example.project.data.songs.Song
//
//class CreatePostViewModel : ViewModel() {
//
//    var createPostUiState: CreatePostUiState by mutableStateOf(CreatePostUiState.Init)
//
//    fun uploadPost(
//        postTitle: String, postBody: String, postSong: Song
//    ) {
//        createPostUiState = CreatePostUiState.LoadingPostUpload
//        val newPost = Post(
//            FirebaseAuth.getInstance().uid!!,
//            FirebaseAuth.getInstance().currentUser?.email!!,
//            postTitle,
//            postBody,
//            postSong
//        )
//        val postsCollection = FirebaseFirestore.getInstance().collection(
//            "posts")
//        postsCollection.add(newPost)
//            .addOnSuccessListener{
//                createPostUiState = CreatePostUiState.PostUploadSuccess
//            }
//            .addOnFailureListener{
//                createPostUiState = CreatePostUiState.ErrorDuringPostUpload(
//                    "Post upload failed")
//            }
//    }
//
//}
//
//sealed interface CreatePostUiState {
//    object Init : CreatePostUiState
//    object LoadingPostUpload : CreatePostUiState
//    object PostUploadSuccess : CreatePostUiState
//    data class ErrorDuringPostUpload(val error: String?) : CreatePostUiState
//}