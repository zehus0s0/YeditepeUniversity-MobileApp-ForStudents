package com.example.myapplication

import com.example.myapplication.Views.ResetPassword.ResetPasswordScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.example.myapplication.Views.HomeView
import com.example.myapplication.Views.LoginView.LoginPage
import com.example.myapplication.ui.theme.MyApplicationTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme { // Uygulama teması burada uygulanıyor
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = "home"
                ) {
                    composable("home") {
                        HomeView(navController = navController)
                    }
                }
            }
        }
    }
}


@Composable
fun NavigationBarSection(navController: NavHostController) {
    NavigationBar {
        NavigationBarItem(
            icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
            label = { Text("Home") },
            selected = navController.currentBackStackEntryAsState().value?.destination?.route == "home",
            onClick = { navController.navigate("home") }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Person, contentDescription = "Account") },
            label = { Text("Account") },
            selected = navController.currentBackStackEntryAsState().value?.destination?.route == "account",
            onClick = { navController.navigate("account") }
        )
    }
}