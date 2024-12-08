package com.example.myapplication.Views.LoginView

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.rounded.Calculate
import androidx.compose.material.icons.rounded.Login
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            setContent {
                MaterialTheme {
                    Surface {
                        LoginPage()
                    }
                }
            }
        } catch (e: Exception) {
            Log.e("MainActivity", "App Crash", e)
            throw e
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginPage() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFFF3F3F3))
            .padding(20.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
        ) {
            // Başlık (Sola hizalanmış)
            HeadCircle()
            WelcomeSection() // Sola hizalanacak
            Spacer(modifier = Modifier.height(16.dp))

            // Kullanıcı adı textfield
            UserNameView()

            Spacer(modifier = Modifier.height(8.dp))

            // Şifre textfield
            PasswordView()

            Spacer(modifier = Modifier.height(8.dp))

            // Şifremi unuttum yazısı
            ForgotPasswordText()

            Spacer(modifier = Modifier.height(40.dp))

            // LogIn butonu (Ortada)
            Box(
                modifier = Modifier
                    .padding(30.dp)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                LogInButton()
            }
        }
    }
}


@Composable
fun HeadCircle() {
    val circleRadius = 800.dp // Dairenin yarıçapı

    Canvas(modifier = Modifier.fillMaxWidth().height(80.dp)) {
        // Dairenin merkezini değiştirelim
        val circleCenterX = size.width / 2 // Yatayda ekranın ortasında
        val circleCenterY = -circleRadius.toPx() + 50f // Dikeyde daha yukarıya kaydırdık

        // Dairenin çizilmesi
        drawCircle(
            color = Color(0xFF1E3A5F), // Dairenin rengi
            radius = circleRadius.toPx(), // Yarıçap
            center = Offset(circleCenterX, circleCenterY) // Dairenin merkezi
        )
    }
}


@Composable
fun WelcomeSection(){
    Text(
        text = "Hello Student!",
        style = MaterialTheme.typography.headlineMedium,
        fontSize = 35.sp,
        color = Color(0xFF342E37),
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Start,
        modifier = Modifier.padding(bottom = 4.dp)
    )
    Text(
        text = "Log in with your OBS information",
        fontSize = 16.sp,
        color = Color(0xFF88B04B),
        modifier = Modifier.padding(bottom = 16.dp)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserNameView() {
    var usernameText by remember { mutableStateOf("") }
    val customColor = Color(0xFF718A39) // Ortak renk

    OutlinedTextField(
        value = usernameText,
        onValueChange = { usernameText = it },
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp) // Yüksekliği ayarlayarak boyutu küçültüyoruz
            .padding(8.dp),
        shape = RoundedCornerShape(20.dp), // Daha yuvarlak köşeler
        label = { Text("Username", color = customColor) },
        leadingIcon = {
            Icon(
                imageVector = Icons.Filled.Person,
                contentDescription = "Username Icon",
                tint = customColor
            )
        },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = customColor,
            unfocusedBorderColor = customColor,
            cursorColor = customColor,
            containerColor = Color.Transparent, // Şeffaf arka plan
            focusedLabelColor = customColor,   // Label odaklanınca
            unfocusedLabelColor = customColor, // Label odaklanmayınca
            focusedLeadingIconColor = customColor, // İkon odaklanınca
            unfocusedLeadingIconColor = customColor // İkon odaklanmayınca
        )
    )
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordView() {
    var passwordText by remember { mutableStateOf("") }
    val customColor = Color(0xFF718A39)

    OutlinedTextField(
        value = passwordText,
        onValueChange = { passwordText = it },
        shape = RoundedCornerShape(20.dp), // Daha yuvarlak köşeler
        modifier = Modifier
            .fillMaxWidth() // Genişliği tam alacak şekilde
            .height(60.dp) // Yüksekliği ayarlayarak boyutu küçültüyoruz
            .padding(8.dp), // İçerik ile kenar arasındaki boşluğu ayarlıyoruz // İçerik ile kenar arasındaki boşluğu ayarlıyoruz
        label = { Text("Password", color = customColor) },
        leadingIcon = {
            Icon(
                imageVector = Icons.Filled.Lock,
                contentDescription = "Password Icon",
                tint = customColor
            )
        },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = customColor,
            unfocusedBorderColor = customColor,
            cursorColor = customColor,
            containerColor = Color.Transparent
        ),
        visualTransformation = PasswordVisualTransformation()
    )
}

@Composable
fun ForgotPasswordText() {
    Text(
        text = buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    color = Color(0xFF718A39),
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    textDecoration = TextDecoration.Underline // Alt çizgi ekleme
                )
            ) {
                append("I forgot my password")
            }
        },
        modifier = Modifier
            .fillMaxWidth() // Genişliği tam olarak ayarla
            .clickable {
                // Handle forgot password click
            },
        textAlign = TextAlign.End // Sağa hizalama
    )
}


@Composable
fun LogInButton() {
    Button(
        onClick = {
            // Handle login button click
        },
        modifier = Modifier
            .height(50.dp)
            .width(150.dp)
            .shadow(
                elevation = 10.dp, // Gölge yüksekliğini artırdık
                shape = RoundedCornerShape(50), // Yuvarlak köşelerle gölge
                ambientColor = Color.Black.copy(alpha = 0.3f), // Gölgenin renk tonu
                spotColor = Color.Black.copy(alpha = 0.4f) // Gölgenin spot rengi
            ),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF9EC7F2),
            contentColor = Color.White
        )
    ) {
        Text(
            fontSize = 16.sp,
            text = "Log in"
        )
        Spacer(modifier = Modifier.width(8.dp)) // Metin ile ikon arasında boşluk ekledik
        Icon(
            imageVector = Icons.Rounded.Login,
            contentDescription = "Icon",
            tint = Color.White
        )
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewLoginPage() {
    LoginPage()
}