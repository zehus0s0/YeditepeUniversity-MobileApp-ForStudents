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
import androidx.lifecycle.viewmodel.compose.viewModel

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
fun LoginPage(viewModel: LoginViewModel = viewModel()) {
    val username by viewModel.username.collectAsState()
    val password by viewModel.password.collectAsState()
    val loginStatus by viewModel.loginStatus.collectAsState()

    var isForgotPasswordClicked by remember { mutableStateOf(false) }

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
            HeadCircle()

            if (!isForgotPasswordClicked) {
                WelcomeSection()
            } else {
                Text(
                    text = "Password Recovery",
                    style = MaterialTheme.typography.headlineMedium,
                    fontSize = 35.sp,
                    color = Color(0xFF342E37),
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Kullanıcı adı textfield
            UserNameView(
                value = username,
                onValueChange = viewModel::onUsernameChanged
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Şifre textfield
            if (!isForgotPasswordClicked) {
                PasswordView(
                    value = password,
                    onValueChange = viewModel::onPasswordChanged
                )
            } else {
                PhoneNumberTextField(
                    viewModel = viewModel
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            if (!isForgotPasswordClicked) {
                ForgotPasswordText {
                    isForgotPasswordClicked = true
                }
            }

            Spacer(modifier = Modifier.height(40.dp))

            // LogIn Butonu
            Box(
                modifier = Modifier
                    .padding(30.dp)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                LogInButton(
                    isForgotPasswordClicked = isForgotPasswordClicked,
                    onClick = {
                        if (isForgotPasswordClicked) {

                        } else {
                            viewModel.login()
                        }
                    }
                )
            }

            // Durum mesajı
            loginStatus?.let {
                Text(
                    text = it,
                    color = Color.Red,
                    modifier = Modifier.padding(top = 16.dp),
                    textAlign = TextAlign.Center
                )
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
fun UserNameView(value: String, onValueChange: (String) -> Unit) {
    var usernameText by remember { mutableStateOf("") }
    val customColor = Color(0xFF718A39) // Ortak renk

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
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
fun PasswordView(value: String, onValueChange: (String) -> Unit) {
    val customColor = Color(0xFF718A39)

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhoneNumberTextField(viewModel: LoginViewModel) {
    var phoneNumberText by remember { mutableStateOf("") }

    OutlinedTextField(
        value = phoneNumberText,
        onValueChange = {
            phoneNumberText = it
            viewModel.resetPassword(it)
        },
        label = { Text("Phone Number") },
        leadingIcon = {
            Icon(
                imageVector = Icons.Filled.Lock,
                contentDescription = "Password Icon"
            )
        },
        modifier = Modifier.fillMaxWidth().height(60.dp),
        shape = RoundedCornerShape(20.dp),
        colors = TextFieldDefaults.outlinedTextFieldColors()
    )
}

@Composable
fun ForgotPasswordText(onClick: () -> Unit) {
    Text(
        text = buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    color = Color(0xFF718A39),
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    textDecoration = TextDecoration.Underline
                )
            ) {
                append("I forgot my password")
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        textAlign = TextAlign.End
    )
}



@Composable
fun LogInButton(
    isForgotPasswordClicked: Boolean,
    onClick: () -> Unit // onClick parametresi eklendi
) {
    Button(
        onClick = onClick, // Parametre burada kullanıldı
        modifier = Modifier
            .height(50.dp)
            .width(150.dp)
            .shadow(
                elevation = 10.dp,
                shape = RoundedCornerShape(50),
                ambientColor = Color.Black.copy(alpha = 0.3f),
                spotColor = Color.Black.copy(alpha = 0.4f)
            ),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF9EC7F2),
            contentColor = Color.White
        )
    ) {
        Text(
            fontSize = 16.sp,
            text = if (isForgotPasswordClicked) "Reset Password" else "Log in"
        )
        Spacer(modifier = Modifier.width(8.dp))
        Icon(
            imageVector = if (isForgotPasswordClicked) Icons.Filled.Refresh else Icons.Rounded.Login,
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