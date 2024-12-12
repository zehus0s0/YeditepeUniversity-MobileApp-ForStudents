package com.example.myapplication.Views.ResetPassword

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.Utilities.Constants
import com.example.myapplication.Views.LoginView.LoginViewModel
import com.example.myapplication.Views.LoginView.UserNameView

class ResetPasswordScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            setContent {
                val navController = rememberNavController()
                val resetPasswordViewModel: ResetPasswordViewModel = viewModel()
                MaterialTheme {
                    Surface {
                        ResetPasswordScreen(navController = navController, viewModel = resetPasswordViewModel)
                    }
                }
            }
        } catch (e: Exception) {
            Log.e("ResetPasswordScreen", "App Crash", e)
            throw e
        }
    }
}

@Composable
fun ResetPasswordScreen(navController: NavHostController, viewModel: ResetPasswordViewModel = viewModel()) {
    val username by viewModel.username.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Constants.hubWhite)
            .padding(20.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Password Recovery",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = Constants.hubBlack,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            UserNameView(
                value = username,
                onValueChange = viewModel::onUsernameChanged,
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { navController.popBackStack() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Back to Login")
            }
        }
    }
}

@Preview
@Composable
fun PreviewForgetPasswordScreen() {
    // Mock bir NavController sağlıyoruz.
    val mockNavController = rememberNavController()
    ResetPasswordScreen()
}
