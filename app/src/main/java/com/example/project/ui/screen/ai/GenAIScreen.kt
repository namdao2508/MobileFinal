package com.example.project.ui.screen.ai

import android.graphics.Paint.Align
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.project.data.posts.Post
import com.example.project.data.songs.Song
import com.example.project.ui.screen.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GenAIScreen(
    viewModel: GenAIViewModel = hiltViewModel(),
    MainViewModel: MainViewModel = hiltViewModel(),
    modifier: Modifier = Modifier,
    onBack: () -> Unit = {},
    onHome: () -> Unit = {},
    onProfile: () -> Unit = {},
    onPostCreated: (Post) -> Unit = {} // Callback to send created post to MainScreen
) {
    var currentScreen by rememberSaveable { mutableStateOf("main") }
    var textResult = viewModel.textGenerationResult.collectAsState().value
    var mood = rememberSaveable { mutableStateOf("") }
    var searchQuery = rememberSaveable { mutableStateOf("") }
    var selectedSong: Song? by remember { mutableStateOf<Song?>(null) }
    var showCreatePostDialog by rememberSaveable { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp) // Default height of the TopAppBar
                    ) {
                        IconButton(
                            onClick = {
                                if (currentScreen != "main") {
                                    viewModel.clearTextResults() // Clear textResults on back
                                    currentScreen = "main"
                                    mood.value = ""
                                    searchQuery.value = ""
                                }else{
                                    onBack()
                                }
                            },
                            modifier = Modifier.align(Alignment.CenterStart)
                        ) {
                            Icon(
                                Icons.Default.ArrowBack,
                                contentDescription = "Back"
                            )
                        }

                        Text(
                            text = "New Post",
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }
            )
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
                        onClick = { onHome() },
                        modifier = Modifier.size(48.dp) // Explicit size for the button
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Home,
                            contentDescription = "Home",
                            modifier = Modifier.size(24.dp) // Size for the icon
                        )
                    }

                    IconButton(
                        onClick = { onProfile() },
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
        when (currentScreen) {
            "main" -> {
                // Main screen layout
                Column(
                    modifier = modifier
                        .fillMaxSize(), // No padding, centered within the device height
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Button(
                        onClick = { currentScreen = "searchSongs" },
                        modifier = Modifier.fillMaxWidth(0.8f)
                    ) {
                        Text("Search Songs")
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = { currentScreen = "recommendationsByMood"},
                        modifier = Modifier.fillMaxWidth(0.8f)
                    ) {
                        Text("Song Recommendations by Mood")
                    }
                }
            }

            "searchSongs" -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues) // Account for top bar height
                        .padding(16.dp), // Additional padding for content
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {
                    OutlinedTextField(
                        value = searchQuery.value,
                        onValueChange = { searchQuery.value = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        label = { Text("What song do you want to search?") },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Search Icon"
                            )
                        }
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Button(
                        onClick = {
                            viewModel.searchSongs(query = searchQuery.value) // Trigger the search
                        },
                        modifier = Modifier.align(Alignment.End)
                    ) {
                        Text("Search Song")
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    if (!textResult.isNullOrEmpty()) {
                        val songs = viewModel.parseSongs(textResult)

                        Text(
                            text = "Search Results:",
                            modifier = Modifier.padding(bottom = 8.dp)
                        )

                        LazyColumn(modifier = Modifier.fillMaxSize()) {
                            items(songs) { song ->
                                SongCard(
                                    song = song,
                                    onClick = { selectedSong = song; showCreatePostDialog = true }
                                )
                            }
                        }
                    }
                }
            }

            "recommendationsByMood" -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues) // Account for top bar height
                        .padding(16.dp), // Additional padding for content
                    verticalArrangement = Arrangement.Top
                ) {
                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        value = mood.value,
                        onValueChange = { mood.value = it },
                        label = { Text("Enter your mood") },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Search Icon"
                            )
                        }
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Button(
                        onClick = {
                            viewModel.generateAI(mood = mood.value) // Trigger mood-based recommendations
                        },
                        modifier = Modifier.align(Alignment.End)
                    ) {
                        Text("Get Song Recommendations")
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Display recommendations as cards
                    if (!textResult.isNullOrEmpty()) {
                        val songs = viewModel.parseSongs(textResult) // Parse the text results into Song objects

                        Text(
                            text = "Recommended Songs:",
                            modifier = Modifier.padding(bottom = 8.dp)
                        )

                        LazyColumn(modifier = Modifier.fillMaxSize()) {
                            items(songs) { song ->
                                SongCard(
                                    song = song,
                                    onClick = { currentSong ->
                                        // Handle song click
                                        selectedSong = currentSong;
                                        showCreatePostDialog = true
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }

        // Show dialog when a song is selected
        if (showCreatePostDialog && selectedSong != null) {
            CreatePostDialog(
                song = selectedSong!!,
                onPostCreate = { title, body ->
                    val newPost = Post(title = title, body = body, song = selectedSong!!)
//                    MainViewModel.addPost(newPost) // Add the post to the MainViewModel
                    //viewModel.createPost(title, body, selectedSong!!) // Add the post to the GenAIViewModel(newPost)  // Send the post to the MainScreen
                    //DataManager.posts.plus(newPost)
                    showCreatePostDialog = false
                },
                onDismiss = { showCreatePostDialog = false }
            )
        }
    }
    }

@Composable
fun SongCard(song: Song, onClick: (Song) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick(song) }, // Trigger onClick when clicked
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(text = song.title, style = MaterialTheme.typography.bodyLarge)
            Text(text = "By: ${song.artist}", style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
fun CreatePostDialog(
    song: Song,
    onPostCreate: (String, String) -> Unit,
    onDismiss: () -> Unit
) {
    var postTitle by rememberSaveable { mutableStateOf("") }
    var postBody by rememberSaveable { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Create Post") },
        text = {
            Column {
                OutlinedTextField(
                    value = postTitle,
                    onValueChange = { postTitle = it },
                    label = { Text("Post Title") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = postBody,
                    onValueChange = { postBody = it },
                    label = { Text("Post Body") },
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 4
                )
            }
        },
        confirmButton = {
            TextButton(onClick = {
                onPostCreate(postTitle, postBody)
                onDismiss()
            }) {
                Text("Post")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

