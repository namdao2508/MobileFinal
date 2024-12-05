package com.example.project.ui.screen.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.project.ui.screen.ai.GenAIViewModel
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold


@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    onLogout: () -> Unit = {},
    onHome: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                // Log Out Button in top right corner
                Button(
                    onClick = { onLogout() },
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(16.dp), // Padding from the edges
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error // Red color
                    )
                ) {
                    Text("Log Out", color = MaterialTheme.colorScheme.onError) // Text in white
                }
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
                        onClick = { onHome() },
                        modifier = Modifier.size(48.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Home,
                            contentDescription = "Home",
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            // Your profile content here
            Text("Profile Screen", modifier = Modifier.padding(16.dp))
        }
    }
}





