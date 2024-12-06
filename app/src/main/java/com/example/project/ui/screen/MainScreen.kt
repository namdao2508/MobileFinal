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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.project.R
import com.example.project.data.posts.Post
import com.example.project.data.songs.Song

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
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.intune), color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                ),
                actions = {
                    IconButton(
                        onClick = {/*Go to Search screen*/ }
                    ) {
                        Icon(Icons.Filled.Search, contentDescription = stringResource(R.string.search_for_friends))
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
                    contentDescription = stringResource(R.string.add),
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
                            contentDescription = stringResource(R.string.profile),
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
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(postsList) { post ->
                        PostCard(
                            post = post,
                            onRemovePost = { mainViewModel.removePost(it) },
                            onEditPost = { updatedPost -> mainViewModel.editPost(post, updatedPost) }
                        )
                    }
                }
            } else {
                Text(
                    text = stringResource(R.string.no_posts_available),
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}

@Composable
fun PostCard(
    post: Post,
    onRemovePost: (Post) -> Unit,
    onEditPost: (Post) -> Unit // Callback for updating the post
) {
    var isExpanded by remember { mutableStateOf(false) } // State to toggle card expansion
    var showEditDialog by remember { mutableStateOf(false) } // State to show edit dialog

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { isExpanded = !isExpanded } // Toggle expanded state on click
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = post.title,
                style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = post.body,
                style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = stringResource(R.string.song_by, post.songTitle, post.artist))

            if (isExpanded) {
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    IconButton(onClick = { showEditDialog = true }) {
                        Icon(
                            imageVector = Icons.Filled.Edit,
                            contentDescription = stringResource(R.string.edit),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                    IconButton(onClick = { onRemovePost(post) }) {
                        Icon(
                            imageVector = Icons.Filled.Delete,
                            contentDescription = stringResource(R.string.delete),
                            tint = Color.Red
                        )
                    }
                }
            }
        }
    }

    // Show the Edit Dialog
    if (showEditDialog) {
        EditPostDialog(
            post = post,
            onPostUpdate = { updatedPost ->
                onEditPost(updatedPost) // Update the post
                showEditDialog = false // Close the dialog
            },
            onDismiss = { showEditDialog = false } // Close the dialog on cancel
        )
    }
}

@Composable
fun EditPostDialog(
    post: Post,
    onPostUpdate: (Post) -> Unit,
    onDismiss: () -> Unit
) {
    var postTitle by rememberSaveable { mutableStateOf(post.title) } // Pre-fill with existing title
    var postBody by rememberSaveable { mutableStateOf(post.body) }  // Pre-fill with existing body

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(stringResource(R.string.edit_post)) },
        text = {
            Column {
                OutlinedTextField(
                    value = postTitle,
                    onValueChange = { postTitle = it },
                    label = { Text(stringResource(R.string.post_title)) },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = postBody,
                    onValueChange = { postBody = it },
                    label = { Text(stringResource(R.string.post_body)) },
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 4
                )
            }
        },
        confirmButton = {
            TextButton(onClick = {
                val updatedPost = post.copy(
                    title = postTitle,
                    body = postBody
                ) // Create an updated post
                onPostUpdate(updatedPost)
            }) {
                Text(stringResource(R.string.update))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.cancel))
            }
        }
    )
}

