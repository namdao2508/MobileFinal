package com.example.project.ui.navigation

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Splash: Screen("splash")
    object Messages : Screen("messages")
    object AddPost : Screen("add post")
    object GenAI : Screen("gen ai")
    object Main : Screen("main")
    object Profile: Screen("Profile")
}