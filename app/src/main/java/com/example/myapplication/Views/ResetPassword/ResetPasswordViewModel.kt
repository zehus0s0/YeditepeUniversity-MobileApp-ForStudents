package com.example.myapplication.Views.ResetPassword

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ResetPasswordViewModel : ViewModel() {
    private val _username = MutableStateFlow("")
    val username = _username.asStateFlow()

    private val _phoneNumber = MutableStateFlow("")
    val phoneNumber = MutableStateFlow("")

    fun onUsernameChanged(newUsername: String) {
        _username.value = newUsername
    }

    fun onPhoneNumberChanged(newPhoneNumber: String) {
        _phoneNumber.value = newPhoneNumber
    }

    // Şifre sıfırlama işlemi
    fun resetPassword() {}
}
