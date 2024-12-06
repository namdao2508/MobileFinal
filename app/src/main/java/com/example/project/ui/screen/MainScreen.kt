package com.example.project.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.project.data.posts.Post
import com.example.project.data.songs.Song
import com.example.project.ui.theme.ProjectTheme

//import hu.bme.aut.aitforum.data.Post
//import net.engawapg.lib.zoomable.rememberZoomState
//import net.engawapg.lib.zoomable.zoomable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    mainViewModel: MainViewModel = hiltViewModel(),
    posts: List<Post> = listOf(),
    onAddPost: () -> Unit = {},
    onProfile: () -> Unit = {},
    onSongClick: (Song) -> Unit = {}
) {
    val postsList by mainViewModel.getAllPosts().collectAsState(emptyList())
    ProjectTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("InTune") },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer
                    ),
                    actions = {
                        IconButton(
                            onClick = {/*Go to Search screen*/ }
                        ) {
                            Icon(Icons.Filled.Search, contentDescription = "Search For Friends")
                        }
                    }
                )
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = { onAddPost() }
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
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.primary,
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        IconButton(
                            onClick = { onProfile() },
                            modifier = Modifier.size(48.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Person,
                                contentDescription = "Profile",
                                modifier = Modifier.size(24.dp),
                                tint = MaterialTheme.colorScheme.secondary
                            )
                        }
                    }
                }
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                if (postsList.isNotEmpty()) {
                    Text(
                        text = "Posts:",
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        items(postsList) { post ->
                            PostCard(post = post,
                                onRemovePost = { mainViewModel.removePost(post) })
                        }
                    }
                } else {
                    Text(
                        text = "No posts available.",
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }

//@Composable
//fun PostCard(post: Post,
//             onRemovePost: (Post) -> Unit)
// {
//    Card(
//        modifier = Modifier.fillMaxWidth().padding(8.dp)
//    ) {
//        Row(
//            verticalAlignment = Alignment.CenterVertically,
//            modifier = Modifier.padding(20.dp)
//        ) {
//            Column(modifier = Modifier.padding(16.dp)) {
//                Text(text = post.title, style = MaterialTheme.typography.bodyLarge)
//                Spacer(modifier = Modifier.height(8.dp))
//                Text(text = post.body)
//                Spacer(modifier = Modifier.height(8.dp))
//                Text(text = "Song: ${post.songTitle} by ${post.artist}")
//            }
//
//            Icon(
//                imageVector = Icons.Filled.Clear,
//                contentDescription = "Delete",
//                modifier = Modifier.clickable { onRemovePost(post) },
//                tint = Color.Red
//            )
//        }
//    }
//}
}
@Composable
fun PostCard(
    post: Post,
    onRemovePost: (Post) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp), // Adds a shadow for a card-like appearance
        shape = RoundedCornerShape(8.dp) // Slightly rounded corners
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp) // Adds padding inside the card
        ) {
            // Title as the largest text
            Text(
                text = post.title,
                style = MaterialTheme.typography.titleLarge,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Body as a subheading
            Text(
                text = post.body,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Song information at the bottom
            Text(
                text = "Song: ${post.songTitle} by ${post.artist}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Row for actions (like delete)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Icon(
                    imageVector = Icons.Filled.Clear,
                    contentDescription = "Delete",
                    modifier = Modifier
                        .clickable { onRemovePost(post) }
                        .padding(4.dp)
                        .size(24.dp), // Sets a uniform size for the icon
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}

