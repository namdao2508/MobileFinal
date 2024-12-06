package com.example.project.ui.screen.login

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch


@Composable
fun LogInScreen(
    modifier: Modifier = Modifier,
    viewModel: LogInViewModel = viewModel(),
    onLoginSuccess: () -> Unit = {}
) {

//    var showPassword by rememberSaveable { mutableStateOf(false) }
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }

    val coroutineScope = rememberCoroutineScope()

    Box(modifier = modifier) {
        Text(
            text = "InTune",
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 50.dp),
            fontSize = 30.sp
        )
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Email input field
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(0.8f),
                label = {
                    Text(text = "E-mail")
                },
                value = email,
                onValueChange = {
                    email = it
                },
                singleLine = true,
//                leadingIcon = {
//                    Icon(Icons.Default.Email, null)
//                }
            )

            // Password input field
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(0.8f),
                label = {
                    Text(text = "Password")
                },
                value = password,
                onValueChange = { password = it },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation()
//                if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
//                trailingIcon = {
//                    IconButton(onClick = { showPassword = !showPassword }) {
//                        if (showPassword) {
//                            Icon(Icons.Default.Clear, null)
//                        } else {
//                            Icon(Icons.Default.Info, null)
//                        }
//                    }
//                }
            )

            // Login and Register Buttons
            Row(
                modifier = Modifier.fillMaxWidth(0.8f),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(onClick = {
                    // Login
                    coroutineScope.launch {
                        val result = viewModel.loginUser(email, password)
                        if (result?.user != null) {
                            // Navigate to next screen on successful login
                            onLoginSuccess()
                        }
                    }
                }) {
                    Text(text = "Login")
                }
                Button(onClick = {
                    // Register
                    coroutineScope.launch {
                        viewModel.registerUser(email, password)
                    }
                }) {
                    Text(text = "Register")
                }
            }
        }

        // Bottom Column for Loading/Success/Error Messages
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(bottom = 50.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when (viewModel.loginUiState) {
                is LoginUiState.Init -> {}
                is LoginUiState.Loading -> {
                    CircularProgressIndicator()
                }
                is LoginUiState.RegisterSuccess -> {
                    Text("Registration Successful")
                }
                is LoginUiState.LoginSuccess -> {
                    Text("Login Successful")
                }
                is LoginUiState.Error -> {
                    Text(
                        text = "Error: ${(viewModel.loginUiState as LoginUiState.Error).errorMessage}",
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}
