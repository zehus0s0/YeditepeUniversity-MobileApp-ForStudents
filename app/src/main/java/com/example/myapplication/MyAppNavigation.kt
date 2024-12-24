package com.example.myapplication

import SplashScreen
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.myapplication.Views.ChatScreen.ChatView
import com.example.myapplication.Views.CourseView.CoursesScreen
import com.example.myapplication.Views.GroupsView.GroupsScreen
import com.example.myapplication.Views.HomeView
import com.example.myapplication.Views.LoginView.LoginPage
import com.example.myapplication.Views.LoginView.LoginViewModel
import com.example.myapplication.Views.ResetPassword.ResetPasswordScreen
import com.example.myapplication.Views.ResetPassword.ResetPasswordViewModel
import com.example.myapplication.Views.ReviewScreen.ReviewScreen // ReviewScreen'i import ettik.

@Composable
fun MyAppNavigation(
    modifier: Modifier = Modifier,
    loginViewModel: LoginViewModel
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "splash"
    ) {
        composable("splash") {
            SplashScreen {
                navController.navigate("login") {
                    popUpTo("splash") { inclusive = true }
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

        composable(
            "chat/{chatId}",
            arguments = listOf(navArgument("chatId") { type = NavType.StringType })
        ) { backStackEntry ->
            val chatId = backStackEntry.arguments?.getString("chatId") ?: ""
            ChatView(chatId = chatId)
        }

        composable("courses") {
            CoursesScreen(
                onNavigateBack = { navController.navigateUp() }
            )
        }

        composable("groups") {
            GroupsScreen()
        }

        // Review ekranı
        composable("reviews") {
            ReviewScreen(
                reviews = emptyList(), // Örnek veriler veya ViewModel'den alınan verilerle doldurulabilir.
                onTeacherReviewClick = { navController.navigate("teacherReviews") },
                onCourseReviewClick = { navController.navigate("courseReviews") }
            )
        }
    }
}
