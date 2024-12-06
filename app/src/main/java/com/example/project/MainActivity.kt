package com.example.project

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
//import androidx.compose.ui.graphics.BlendMode.Companion.Screen
import com.example.project.ui.screen.ai.GenAIScreen
import com.example.project.ui.screen.login.LogInScreen
import com.example.project.ui.theme.ProjectTheme
import com.example.project.ui.navigation.Screen
//import com.example.project.ui.screen.messages.MessagesScreen
//import com.example.project.ui.screen.writemessage.WriteMessageScreen
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.project.ui.screen.MainScreen
import com.example.project.ui.screen.MainViewModel
import com.example.project.ui.screen.profile.ProfileScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay

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
    mainViewModel: MainViewModel = viewModel(),

//) {
//
//
//    NavHost(
//        modifier = modifier,
//        navController = navController,
//        startDestination = Screen.Login.route
//    ) {
//
 startDestination: String = "splash"
) {
    NavHost(navController = navController, startDestination = startDestination
    ) {
        composable("splash") {
            SplashScreen(navController) // Display splash page first
        }

//        composable("Main") {
//            MainScreen()
//        }


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
                onPostCreated = { post ->
                    // Add post to ViewModel or handle navigation back to MainScreen
                    mainViewModel.addPost(post)
                    navController.popBackStack() // Navigate back to MainScreen

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

@Composable
fun SplashScreen(navController: NavController) {
    LaunchedEffect(Unit) {
        delay(3000) // 3-second delay
        navController.navigate("login") {
            popUpTo("splash") { inclusive = true }
        }
    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "App logo",
                modifier = Modifier
                    .size(200.dp)
                    .padding(16.dp)
            )
        }
    }
}
