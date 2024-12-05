package com.example.project.ui.screen.profile

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.Scaffold


@Composable
fun ProfileScreen (
//    viewModel: ProfileViewModel = viewModel<>(),
    modifier: Modifier = Modifier,
    onHome: () -> Unit = {}
) {
    Text("Profile Screen")

    Scaffold(
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
                }
            }
        }

    ) { paddingValues ->

        Column(modifier = Modifier.padding(paddingValues)) {


        }

    }
}




