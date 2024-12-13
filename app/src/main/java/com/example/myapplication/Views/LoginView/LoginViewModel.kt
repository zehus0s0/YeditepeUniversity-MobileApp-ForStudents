package com.example.myapplication.Views.LoginView

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class LoginViewModel : ViewModel() {

    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    // E-posta ve şifre state'leri
    private val _email = MutableStateFlow("")
    val email = _email.asStateFlow()

    private val _password = MutableStateFlow("")
    val password = _password.asStateFlow()


    private val _navigateToHome = MutableStateFlow(false)
    val navigateToHome = _navigateToHome.asStateFlow()
    // Hata mesajı veya başarı durumu için state
    private val _loginStatus = MutableStateFlow<String?>(null)
    val loginStatus = _loginStatus.asStateFlow()

    // E-posta güncelleme
    fun onEmailChanged(newEmail: String) {
        _email.value = newEmail
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

        fun setNavigateToHome(navigate: Boolean) {
        _navigateToHome.value = navigate
        if (!emailValue.endsWith("@std.yeditepe.edu.tr")) {
            _loginStatus.value = "Sadece @std.yeditepe.edu.tr uzantılı e-postalar kabul edilir."
            return
        }

        firebaseAuth.signInWithEmailAndPassword(emailValue, passwordValue)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = firebaseAuth.currentUser
                    if (user?.isEmailVerified == true) {
                        _loginStatus.value = "Başarıyla giriş yaptınız!"
                    } else {
                        _loginStatus.value = "Lütfen e-posta adresinizi doğrulayın."
                        sendVerificationEmail(user)
                    }
                } else {
                    _loginStatus.value = "Giriş başarısız: ${task.exception?.message}"
                }
            }
    }

    // E-posta doğrulama gönderme
    private fun sendVerificationEmail(user: FirebaseUser?) {
        user?.sendEmailVerification()?.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                _loginStatus.value = "Doğrulama e-postası gönderildi. Lütfen e-postanızı kontrol edin."
            } else {
                _loginStatus.value = "Doğrulama e-postası gönderilemedi: ${task.exception?.message}"
            }
        }
    }

    // Şifre sıfırlama
    fun resetPassword(email: String) {
        if (email.isEmpty()) {
            _loginStatus.value = "E-posta boş olamaz"
            return
        }
        firebaseAuth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _loginStatus.value = "Şifre sıfırlama e-postası gönderildi."
                } else {
                    _loginStatus.value = "Şifre sıfırlama başarısız: ${task.exception?.message}"
                }
            }
    }
}

