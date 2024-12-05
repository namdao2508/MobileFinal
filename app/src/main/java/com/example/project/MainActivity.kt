package com.example.project

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.BlendMode.Companion.Screen
import com.example.project.ui.screen.ai.GenAIScreen
import com.example.project.ui.screen.login.LogInScreen
import com.example.project.ui.theme.ProjectTheme
import com.example.project.ui.navigation.Screen
import com.example.project.ui.screen.login.LogInScreen
//import com.example.project.ui.screen.messages.MessagesScreen
//import com.example.project.ui.screen.writemessage.WriteMessageScreen
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.project.ui.screen.MainScreen
import com.example.project.ui.screen.ai.Post
import com.example.project.ui.screen.profile.ProfileScreen
import dagger.hilt.android.AndroidEntryPoint

//class MainActivity : ComponentActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//        setContent {
//            ProjectTheme {
//                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                    GenAIScreen(
//                        modifier = Modifier.padding(innerPadding)
//                    )
//                }
//            }
//        }
//    }
//}



@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ProjectTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavGraph(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun NavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {

    var posts by rememberSaveable { mutableStateOf<List<Post>>(emptyList()) }

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Screen.Login.route
    ) {
        composable(Screen.Login.route) {
            LogInScreen(
                onLoginSuccess = {
                    navController.navigate(Screen.Main.route)
                }
            )
        }
        composable(Screen.Main.route) {
            MainScreen(
                onAddPost = {
                    navController.navigate(Screen.GenAI.route)
                },
                onProfile = {
                    navController.navigate(Screen.Profile.route)
                }
            )
        }
        composable(Screen.GenAI.route) {
            GenAIScreen(
                onBack = {
                    navController.navigate(Screen.Main.route)
                },
                onHome = {
                    navController.navigate(Screen.Main.route)
                },
                onProfile = {
                    navController.navigate(Screen.Profile.route)
                },
                onPostCreated = { newPost ->
                    // Add the new post to the list of posts
                    posts = posts + newPost

                    // Navigate to the MainScreen after post is created
                    navController.navigate(Screen.Main.route)
                }
            )
        }

        composable(Screen.Profile.route) {
            ProfileScreen(
                onHome = {
                    navController.navigate(Screen.Main.route)
                },
                onLogout = {
                    navController.navigate(Screen.Login.route)
                }
            )
        }
    }
}
