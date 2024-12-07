package com.example.myapplication.Views.LoginView

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.sp
import com.example.myapplication.Views.MyApp
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.myapplication.R

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            setContent {
                MaterialTheme {
                    Surface {
                        MyApp()
                    }
                }
            }
        } catch (e: Exception) {
            Log.e("MainActivity", "App Crash", e)
            throw e
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginPage() {
    var usernameText by remember { mutableStateOf("") }
    var passwordText by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFF36454F))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Hello Student!",
                style = TextStyle(
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Login with your ODS information",
                style = TextStyle(
                    color = Color.White,
                    fontSize = 16.sp
                )
            )
            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                value = usernameText,
                onValueChange = { usernameText = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Username") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Person,
                        contentDescription = "Username"
                    )
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color(0xFF4CAF50),
                    unfocusedBorderColor = Color(0xFF4CAF50)
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = passwordText,
                onValueChange = { passwordText = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Password") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Lock,
                        contentDescription = "Password"
                    )
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color(0xFF4CAF50),
                    unfocusedBorderColor = Color(0xFF4CAF50)
                ),
                visualTransformation = PasswordVisualTransformation()
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "I forgot my password",
                style = TextStyle(
                    color = Color(0xFF03A9F4),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.clickable {
                    // Handle forgot password click
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    // Handle login button click
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF4CAF50),  // backgroundColor yerine containerColor
                    contentColor = Color.White  // contentColor doğru şekilde kullanılıyor
                )
            ) {
                Text(text = "Log in")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLoginPage() {
    LoginPage()
}