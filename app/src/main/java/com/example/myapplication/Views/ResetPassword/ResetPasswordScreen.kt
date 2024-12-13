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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Login
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.Components.CustomButton
import com.example.myapplication.Utilities.Constants
import com.example.myapplication.Views.LoginView.ForgotPasswordText
import com.example.myapplication.Views.LoginView.HeadCircle
import com.example.myapplication.Views.LoginView.PhoneNumberTextField
import com.example.myapplication.Views.LoginView.UserNameView
import com.example.myapplication.Views.LoginView.WelcomeSection

@Composable
fun ResetPasswordScreen(navController: NavHostController, viewModel: ResetPasswordViewModel = viewModel()) {
    val username by viewModel.username.collectAsState()
    val phoneNumber by viewModel.phoneNumber.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(20.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
        ) {
            HeadCircle()
            WelcomeSection() // Sola hizalanacak
            Spacer(modifier = Modifier.height(16.dp))
            // Kullanıcı adı textfield

            UserNameView(value = username, onValueChange = viewModel::onUsernameChanged)
            Spacer(modifier = Modifier.height(8.dp))
            PhoneNumberTextField(viewModel = viewModel)
            Spacer(modifier = Modifier.height(40.dp))
            CustomButton(
                buttonColor = Constants.hubBabyBlue,
                buttonText = "Log In",
                buttonTextColor = Constants.hubWhite,
                buttonIcon = Icons.Filled.Login,
                buttonHeight = 50,
                buttonWidth = 150
            ) {viewModel.resetPassword()}
        }
    }
}

@Preview
@Composable
fun PreviewForgetPasswordScreen() {
    // Mock bir NavController sağlıyoruz.
    val navController = rememberNavController()
    ResetPasswordScreen(
        navController = navController,
        viewModel = ResetPasswordViewModel()
    )
}
