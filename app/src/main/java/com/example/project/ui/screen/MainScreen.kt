package com.example.project.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.isTraceInProgress
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.HorizontalAlignmentLine
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.firebase.auth.FirebaseAuth
//import hu.bme.aut.aitforum.data.Post
//import net.engawapg.lib.zoomable.rememberZoomState
//import net.engawapg.lib.zoomable.zoomable


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
//    messagesViewModel: MessagesViewModel = viewModel(),
    onWriteMessageClick: () -> Unit = {}
) {
//    val postListState = messagesViewModel.postsList().collectAsState(
//        initial = MessagesUIState.Init)


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Vibe") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor =
                    MaterialTheme.colorScheme.secondaryContainer
                ),
                actions = {
                    IconButton(
                        onClick = {/*Go to Search screen*/}
                    ) {
                        Icon(Icons.Filled.Search, contentDescription = "Search For Friends")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onWriteMessageClick()
                }
            ) {
                Icon(
                    imageVector = Icons.Rounded.Add,
                    contentDescription = "Add",
                    tint = Color.White
                )
            }
        },
        bottomBar = {
            BottomAppBar(
//                modifier = Modifier.height(56.dp), // Compact height
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.primary,
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    IconButton(
                        onClick = { /* Go to Home screen */ },
                        modifier = Modifier.size(48.dp) // Explicit size for the button
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Home,
                            contentDescription = "Home",
                            modifier = Modifier.size(24.dp) // Size for the icon
                        )
                    }

                    IconButton(
                        onClick = { /* Go to Profile screen */ },
                        modifier = Modifier.size(48.dp) // Explicit size for the button
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Person,
                            contentDescription = "Profile",
                            modifier = Modifier.size(24.dp) // Size for the icon
                        )
                    }
                }
            }
        }



    ) { paddingValues ->

        Column(modifier = Modifier.padding(paddingValues)) {
//            if (postListState.value == MessagesUIState.Init) {
//                Text(text = "Initializing..")
//            }
//            else if (postListState.value == MessagesUIState.Loading) {
//                CircularProgressIndicator()
//            } else if (postListState.value is MessagesUIState.Success) {
//                // show messages in a list...
//                LazyColumn() {
//                    items((postListState.value as MessagesUIState.Success).postList){
//                        PostCard(
//                            post = it.post,
//                            onRemoveItem = {
//                                messagesViewModel.deletePost(it.postId)
//                            },
//                            currentUserId = FirebaseAuth.getInstance().uid!!
//                        )
//                    }
//                }
//
//            } else if (postListState.value is MessagesUIState.Error) {
//                // show error...
//            }

        }

    }
}


//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun PostCard(
//    post: Post,
//    onRemoveItem: () -> Unit = {},
//    currentUserId: String = ""
//) {
//    val zoomState = rememberZoomState()
//
//    Card(
//        colors = CardDefaults.cardColors(
//            containerColor = MaterialTheme.colorScheme.surfaceVariant,
//        ),
//        shape = RoundedCornerShape(20.dp),
//        elevation = CardDefaults.cardElevation(
//            defaultElevation = 10.dp
//        ),
//        modifier = Modifier.padding(5.dp).zoomable(zoomState)
//    ) {
//        Column(
//            modifier = Modifier
//                .padding(10.dp)
//        ) {
//            Row(
//                modifier = Modifier.padding(20.dp),
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                Column(
//                    modifier = Modifier
//                        .weight(1f)
//                ) {
//                    Text(
//                        text = "How I'm Feeling...",
//                    )
//                    Text(
//                        text = post.body, // Song artist, title, album cover
//                    )
//                }
//                Row(
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    if (currentUserId.equals(post.uid)) {
//                        Icon(
//                            imageVector = Icons.Filled.Delete,
//                            contentDescription = "Delete",
//                            modifier = Modifier.clickable {
//                                onRemoveItem()
//                            },
//                            tint = Color.Red
//                        )
//                    }
//                }
//            }
//
//            //if (post.imgUrl.isNotEmpty()) {
//            AsyncImage(
//                model = ImageRequest.Builder(LocalContext.current)
//                    //.data(post.imgUrl)
//                    .data("https://firebasestorage.googleapis.com/v0/b/aitforum2024springpeter.appspot.com/o/images%2F02167560-0218-41df-9cbb-cb9787aad275.jpg?alt=media&token=08be5fa2-a202-4f27-9c58-d799b12fe7b2")
//                    .crossfade(true)
//                    .build(),
//                contentDescription = "Image",
//                contentScale = ContentScale.Fit,
//                modifier = Modifier.size(80.dp).zoomable(zoomState)
//            )
//            //}
//        }
//    }
//}


