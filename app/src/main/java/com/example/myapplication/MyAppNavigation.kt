package com.example.myapplication

import SplashScreen
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.Views.ChatScreen.ChatView
import com.example.myapplication.Views.ChatScreen.ChatViewModel
import com.example.myapplication.Views.CourseView.CoursesScreen
import com.example.myapplication.Views.HomeView
import com.example.myapplication.Views.LoginView.LoginPage
import com.example.myapplication.Views.LoginView.LoginViewModel
import com.example.myapplication.Views.ResetPassword.ResetPasswordScreen
import com.example.myapplication.Views.ResetPassword.ResetPasswordViewModel


@Composable
fun MyAppNavigation(
    modifier: Modifier = Modifier,
    loginViewModel: LoginViewModel
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "splash",
        builder = {
            composable("splash") {
                SplashScreen {
                    navController.navigate("login") {
                        popUpTo("splash") { inclusive = true } // Splash ekranını geri yığından kaldır
                    }
                }
            }
            composable("login") {
                LoginPage(modifier, navController, loginViewModel)
            }
            composable("resetpw") {
                ResetPasswordScreen(modifier, navController, ResetPasswordViewModel())
            }
            composable("home") {
                HomeView(modifier, navController, loginViewModel)
            }
            composable("chat") {
                ChatView(chatViewModel = ChatViewModel())
            }

            composable("courses") {
                CoursesScreen() // authViewModel yeniden oluşturulmaz
            }
        }
    )
}

