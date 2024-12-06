package com.example.project.ui.screen.login

//import android.util.Log
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.setValue
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import dagger.hilt.android.lifecycle.HiltViewModel
//import kotlinx.coroutines.delay
//import kotlinx.coroutines.launch
//import javax.inject.Inject
//
//@HiltViewModel
//class LogInViewModel @Inject constructor() : ViewModel() {
//    var email by mutableStateOf("")
//    var password by mutableStateOf("")
//    var isLoading by mutableStateOf(false)
//    var loginMessage by mutableStateOf("")
//
//    fun onEmailChange(newEmail: String) {
//        email = newEmail
//    }
//
//    fun onPasswordChange(newPassword: String) {
//        password = newPassword
//    }
//
//    fun logIn() {
//        isLoading = true
//        loginMessage = ""
//
//        viewModelScope.launch {
//            // Simulate a network delay
//            delay(2000)
//
//            // Mock login validation
//            if (email == "user@example.com" && password == "password123") {
//                loginMessage = "Login successful!"
//                Log.d("LogInViewModel", "Login successful!")  // Add log message for debugging
//
//            } else {
//                loginMessage = "Invalid email or password."
//            }
//
//            isLoading = false
//        }
//    }
//
//    fun register() {
//
//    }
//}

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import kotlinx.coroutines.tasks.await

class LogInViewModel : ViewModel() {

    var loginUiState: LoginUiState by mutableStateOf(LoginUiState.Init)

    private lateinit var auth: FirebaseAuth

    init {
        auth = Firebase.auth
    }

    fun registerUser(email: String, password: String, name: String, toString: String) {
        loginUiState = LoginUiState.Loading
        try {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    loginUiState = LoginUiState.RegisterSuccess
                }
                .addOnFailureListener {
                    loginUiState = LoginUiState.Error(it.message)
                }
        } catch (e: Exception) {
            loginUiState = LoginUiState.Error(e.message)
            e.printStackTrace()
        }
    }

    suspend fun loginUser(email: String, password: String): AuthResult? {
        loginUiState = LoginUiState.Loading
        try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            if (result.user != null) {
                loginUiState = LoginUiState.LoginSuccess
            } else {
                loginUiState = LoginUiState.Error("Login failed")
            }
            return result
        } catch (e: Exception) {
            loginUiState = LoginUiState.Error(e.message)
            e.printStackTrace()
            return null
        }
    }

    // Log out the current user
    fun logOut() {
        auth.signOut()
    }
}

sealed interface LoginUiState {
    object Init: LoginUiState
    object Loading: LoginUiState
    object RegisterSuccess: LoginUiState
    object LoginSuccess: LoginUiState
    data class Error(val errorMessage: String?): LoginUiState
}