package com.example.myapplication

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.Views.HomeView
import com.example.myapplication.Views.HomeView.HomeViewModel
import com.example.myapplication.Views.LoginView.LoginPage
import com.example.myapplication.Views.LoginView.LoginViewModel
import com.example.myapplication.Views.ResetPassword.ResetPasswordScreen
import com.example.myapplication.Views.ResetPassword.ResetPasswordViewModel


@Composable
fun MyAppNavigation(
    modifier: Modifier = Modifier,
    authViewModel: LoginViewModel
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "login",
        builder = {
            composable("login") {
                LoginPage(modifier, navController, authViewModel)
            }
            composable("resetpw") {
                ResetPasswordScreen(modifier, navController, ResetPasswordViewModel())
            }
            composable("home") {
                HomeView(modifier, navController, authViewModel) // authViewModel yeniden oluşturulmaz
            }
        }
    )
}
