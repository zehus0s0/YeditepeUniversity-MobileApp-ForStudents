package com.example.myapplication.Views

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.MainActivity
import com.example.myapplication.Views.LoginView.LoginPage
import com.example.myapplication.Views.ResetPassword.ResetPasswordScreen
import com.google.firebase.FirebaseApp

/*class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        FirebaseApp.initializeApp(this)
        super.onCreate(savedInstanceState)
        Log.d("ActivityLifecycle", "LoginActivity - onCreate")
        setContent {
            val navController = rememberNavController()
            MaterialTheme {
                NavHost(
                    navController = navController,
                    startDestination = "login"
                ) {
                    composable("login") {
                        LoginPage(navController = navController)
                    }

                    composable("reset_password") {
                        ResetPasswordScreen(navController = navController)
                    }

                    // MainActivity'e yönlendirme yapılacak yer
                    composable("main") {
                        // Bu geçişi kullanarak MainActivity'e yönlendirme
                        Log.d("LoginActivity", "Navigating to MainActivity")
                        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                        finish()  // LoginActivity'yi bitiriyoruz
                    }
                }
            }
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        Log.d("ActivityLifecycle", "LoginActivity - onDestroy")
    }
}*/
