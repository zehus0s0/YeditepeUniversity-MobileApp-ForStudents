package com.example.myapplication.Views.LoginView

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

    // Hata mesajı veya başarı durumu için state
    private val _loginStatus = MutableStateFlow<String?>(null)
    val loginStatus = _loginStatus.asStateFlow()

    // E-posta güncelleme
    fun onEmailChanged(newEmail: String) {
        _email.value = newEmail
    }

    // Şifre güncelleme
    fun onPasswordChanged(newPassword: String) {
        _password.value = newPassword
    }

    // Giriş doğrulama
    fun login() {
        val emailValue = _email.value
        val passwordValue = _password.value

        if (emailValue.isEmpty() || passwordValue.isEmpty()) {
            _loginStatus.value = "E-posta veya şifre boş olamaz"
            return
        }

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
