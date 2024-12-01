package com.example.project.ui.navigation

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Messages : Screen("messages")
    object WritePost : Screen("writepost")
    object GenAI : Screen("genai")
    object Main : Screen("main")
}