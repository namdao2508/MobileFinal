package com.example.project.ui.screen.login

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.project.R
import kotlinx.coroutines.launch


@Composable
fun LogInScreen(
    modifier: Modifier = Modifier,
    viewModel: LogInViewModel = viewModel(),
    onLoginSuccess: () -> Unit = {}
) {
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var showCreateAccountDialog by remember { mutableStateOf(false) } // Dialog visibility state
    val coroutineScope = rememberCoroutineScope()

    Box(modifier = modifier) {
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.applogo),
                contentDescription = "App Logo",
                modifier = Modifier
                    .size(120.dp) // Adjust size as needed
                    .padding(bottom = 16.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Email input field
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(0.8f),
                label = {
                    Text(text = "E-mail")
                },
                value = email,
                onValueChange = { email = it },
                singleLine = true
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
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    val image = if (passwordVisible) Icons.Default.Info else Icons.Default.Info
                    val description = if (passwordVisible) "Hide password" else "Show password"
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(imageVector = image, contentDescription = description)
                    }
                }
            )

            // Login Button
            Button(
                onClick = {
                    coroutineScope.launch {
                        val result = viewModel.loginUser(email, password)
                        if (result?.user != null) {
                            // Navigate to next screen on successful login
                            onLoginSuccess()
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(vertical = 16.dp)
            ) {
                Text(text = "Log in")
            }

            // Sign Up Prompt
            TextButton(
                onClick = { showCreateAccountDialog = true },
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Text(text = "Don't have an account? Sign up")
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

    // Create Account Dialog
    if (showCreateAccountDialog) {
        CreateAccountDialog(
            onDismiss = { showCreateAccountDialog = false },
            onAccountCreated = { username, newPassword, name, profilePicUri ->
                coroutineScope.launch {
                    viewModel.registerUser(username, newPassword, name, profilePicUri.toString())
                }
                showCreateAccountDialog = false
            }
        )
    }
}

@Composable
fun CreateAccountDialog(
    onDismiss: () -> Unit,
    onAccountCreated: (String, String, String, Uri?) -> Unit // Corrected to include name and profilePicUri
) {
    var username by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var name by rememberSaveable { mutableStateOf("") }
    var profilePicUri by rememberSaveable { mutableStateOf<Uri?>(null) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Create Account") },
        text = {
            Column {
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Name") },
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = username,
                    onValueChange = { username = it },
                    label = { Text("Username") },
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password") },
                    singleLine = true,
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        val image = if (passwordVisible) Icons.Default.Info else Icons.Default.Info
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(imageVector = image, contentDescription = null)
                        }
                    }
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "Profile Picture",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .fillMaxWidth(), // Ensures the Text fills the available width
                    textAlign = TextAlign.Center // Centers the text horizontally
                )


                Spacer(modifier = Modifier.height(4.dp))
                // Profile Picture Picker
                ImagePicker { uri ->
                    profilePicUri = uri
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onAccountCreated(username, password, name, profilePicUri)
                }
            ) {
                Text("Create Account")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

@Composable
fun ImagePicker(
    onImageSelected: (Uri) -> Unit
) {
    var selectedImageUri by rememberSaveable { mutableStateOf<Uri?>(null) }

    // Launcher to pick an image
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->
            uri?.let {
                selectedImageUri = it
                onImageSelected(it)
            }
        }
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Box(
            modifier = Modifier
                .size(100.dp) // Same size as the icon
                .clip(CircleShape) // Make the box circular
                .border(2.dp, Color.Gray, CircleShape) // Add a circular border
                .clickable { launcher.launch("image/*") }, // Handle image selection
            contentAlignment = Alignment.Center
        ) {
            if (selectedImageUri != null) {
                AsyncImage(
                    model = selectedImageUri,
                    contentDescription = "Selected Image",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            } else {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "Account Icon",
                    modifier = Modifier.size(100.dp), // Match the circle size
                    tint = Color.Gray
                )
            }
        }
    }
}