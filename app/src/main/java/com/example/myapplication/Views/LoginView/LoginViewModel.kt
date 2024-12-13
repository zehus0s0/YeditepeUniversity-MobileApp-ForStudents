package com.example.myapplication.Views.LoginView

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class LoginViewModel : ViewModel() {
    private val _username = MutableStateFlow("")
    val username = _username.asStateFlow()

    private val _password = MutableStateFlow("")
    val password = _password.asStateFlow()

    private val _loginStatus = MutableStateFlow<String?>(null)
    val loginStatus = _loginStatus.asStateFlow()

    private val _navigateToHome = MutableStateFlow(false)
    val navigateToHome = _navigateToHome.asStateFlow()

    fun onUsernameChanged(newUsername: String) {
        _username.value = newUsername
    }

    fun onPasswordChanged(newPassword: String) {
        _password.value = newPassword
    }

    fun login(): Boolean {
        return if (_username.value.isEmpty() || _password.value.isEmpty()) {
            _loginStatus.value = "Username or password cannot be empty"
            false
        } else if (_username.value == "admin" && _password.value == "123") {
            _loginStatus.value = "Login successful!"
            setNavigateToHome(true)  // Navigate to home when login is successful
            true
        } else {
            _loginStatus.value = "Incorrect username or password"
            false
        }
    }

    fun setNavigateToHome(navigate: Boolean) {
        _navigateToHome.value = navigate
    }
}




