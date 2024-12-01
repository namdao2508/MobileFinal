package com.example.project.ui.screen.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LogInViewModel @Inject constructor() : ViewModel() {
    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var isLoading by mutableStateOf(false)
    var loginMessage by mutableStateOf("")

    fun onEmailChange(newEmail: String) {
        email = newEmail
    }

    fun onPasswordChange(newPassword: String) {
        password = newPassword
    }

    fun logIn() {
        isLoading = true
        loginMessage = ""

        viewModelScope.launch {
            // Simulate a network delay
            delay(2000)

            // Mock login validation
            if (email == "user@example.com" && password == "password123") {
                loginMessage = "Login successful!"
            } else {
                loginMessage = "Invalid email or password."
            }

            isLoading = false
        }
    }
}