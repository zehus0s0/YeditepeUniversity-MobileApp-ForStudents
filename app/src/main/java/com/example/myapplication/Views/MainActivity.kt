package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Message
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.example.myapplication.Views.LoginView.LoginPage
import com.example.myapplication.ui.theme.MyApplicationTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                MyApp() // MyApp burada çağrılıyor
            }
        }
    }
}

@Composable
fun MyApp() {
    // NavController oluşturuluyor
    val navController = rememberNavController()

    // Scaffold ve NavigationBar kullanımı
    Scaffold(
        bottomBar = {
            NavigationBarSection(navController = navController)
        }
    ) { paddingValues ->
        // NavHost içinde başlangıç ekranını LoginPage olarak ayarlıyoruz
        NavHost(
            navController = navController,
            startDestination = "login", // Burada LoginPage ekranını başlangıç ekranı yapıyoruz
            modifier = Modifier.padding(paddingValues)
        ) {
            composable("login") { LoginPage() } // LoginPage burada çağrılıyor
            composable("reviews") { ReviewsScreen() }
            composable("account") { AccountScreen() }
            composable("chat") { ChatScreen() }
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
            icon = { Icon(Icons.Default.Star, contentDescription = "Reviews") },
            label = { Text("Reviews") },
            selected = navController.currentBackStackEntryAsState().value?.destination?.route == "reviews",
            onClick = { navController.navigate("reviews") }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Person, contentDescription = "Account") },
            label = { Text("Account") },
            selected = navController.currentBackStackEntryAsState().value?.destination?.route == "account",
            onClick = { navController.navigate("account") }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Message, contentDescription = "Chat") },
            label = { Text("Chat") },
            selected = navController.currentBackStackEntryAsState().value?.destination?.route == "chat",
            onClick = { navController.navigate("chat") }
        )
    }
}

@Composable
fun ReviewsScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFAEBD7)),
        contentAlignment = Alignment.Center
    ) {
        Text("Welcome to Reviews Screen!", fontSize = 24.sp, color = Color.Black)
    }
}

@Composable
fun AccountScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF0FFFF)),
        contentAlignment = Alignment.Center
    ) {
        Text("Welcome to Account Screen!", fontSize = 24.sp, color = Color.Black)
    }
}

@Composable
fun ChatScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE6E6FA)),
        contentAlignment = Alignment.Center
    ) {
        Text("Welcome to Chat Screen!", fontSize = 24.sp, color = Color.Black)
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMyApp() {
    MyApplicationTheme {
        MyApp() // Burada MyApp fonksiyonu preview ediliyor
    }
}
