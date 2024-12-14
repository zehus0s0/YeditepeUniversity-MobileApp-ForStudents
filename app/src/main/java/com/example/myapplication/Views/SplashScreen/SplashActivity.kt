package com.example.myapplication.Views.SplashScreen

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import com.example.myapplication.MainActivity


/*class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val isLoggedIn = checkUserAuthentication() // Kullanıcı girişi kontrolü

        // Eğer kullanıcı giriş yapmışsa ana ekrana git
        if (isLoggedIn) {
            navigateToMainActivity()
        } else {
            // Eğer giriş yapılmamışsa login ekranına git
            navigateToLoginActivity()
        }
    }

    private fun checkUserAuthentication(): Boolean {
        // Kullanıcının kimlik doğrulama durumunu kontrol et
        // Bu, SharedPreferences veya başka bir yerel veritabanı ile yapılabilir
        return false // Kullanıcı giriş yapmamış
    }

    private fun navigateToMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()  // Splash ekranını bitir
    }

    private fun navigateToLoginActivity() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()  // Splash ekranını bitir
    }
}*/
