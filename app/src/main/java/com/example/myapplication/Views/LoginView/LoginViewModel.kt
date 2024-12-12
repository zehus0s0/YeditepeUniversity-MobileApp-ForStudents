package com.example.myapplication.Views.LoginView

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class LoginViewModel : ViewModel() {
    // Kullanıcı adı ve şifre state'leri
    private val _username = MutableStateFlow("")
    val username = _username.asStateFlow()

    private val _password = MutableStateFlow("")
    val password = _password.asStateFlow()

    private val _phoneNumber = MutableStateFlow("")
    val phoneNumber = _phoneNumber.asStateFlow()

    // Hata mesajı veya başarı durumu için state
    private val _loginStatus = MutableStateFlow<String?>(null)
    val loginStatus = _loginStatus.asStateFlow()

    // Kullanıcı adı güncelleme
    fun onUsernameChanged(newUsername: String) {
        _username.value = newUsername
    }

    // Şifre güncelleme
    fun onPasswordChanged(newPassword: String) {
        _password.value = newPassword
    }

    // Giriş doğrulama
    fun login() {
        if (_username.value.isEmpty() || _password.value.isEmpty()) {
            _loginStatus.value = "Kullanıcı adı veya şifre boş olamaz"
        } else if (_username.value == "admin" && _password.value == "123") {
            _loginStatus.value = "Başarıyla giriş yaptınız!"
        } else {
            _loginStatus.value = "Kullanıcı adı veya şifre yanlış"
        }
    }

    fun resetPassword(newPassword: String) {
        _password.value = newPassword
        _loginStatus.value = "Yeni şifre ayarlandı"
    }

    fun onPhoneNumberChanged(newPhoneNumber: String){
        _phoneNumber.value = newPhoneNumber
    }
}


