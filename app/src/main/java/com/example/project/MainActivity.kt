package com.example.project

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
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
            MainScreen()
        }

//        composable(Screen.Messages.route) {
//            MessagesScreen(
//                onWriteMessageClick = {
//                    navController.navigate(Screen.WritePost.route)
//                }
//            )
//        }
//        composable(Screen.WritePost.route) {
//            WriteMessageScreen()
//        }
        composable(Screen.GenAI.route) {
            GenAIScreen()
        }
    }
}
