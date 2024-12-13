package com.example.myapplication.Views.LoginView

import android.content.Intent
import androidx.compose.foundation.Canvas
import androidx.compose.material3.MaterialTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.Components.CustomButton
import com.example.myapplication.Utilities.Constants
import com.example.myapplication.Views.ResetPassword.ResetPasswordViewModel

@Composable
fun LoginPage(
    navController: NavHostController,

) {
    val viewModel: LoginViewModel = viewModel()
    val username by viewModel.username.collectAsState()
    val password by viewModel.password.collectAsState()
    val navigateToHome by viewModel.navigateToHome.collectAsState()

    // Kullanıcı login olduktan sonra navigasyonu kontrol et
    LaunchedEffect(navigateToHome) {
        if (navigateToHome) {
            // Login başarılı olduğunda MainActivity'e yönlendir
            navController.navigate("main") {
                popUpTo("login") { inclusive = true } // Login sayfasını yığından çıkar
            }
        }
    }


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
            PasswordView(value = password, onValueChange = viewModel::onPasswordChanged)
            Spacer(modifier = Modifier.height(16.dp))

            ForgotPasswordText {
                navController.navigate("reset_password")
            }

            Spacer(modifier = Modifier.height(40.dp))
            CustomButton(
                buttonColor = Constants.hubBabyBlue,
                buttonText = "Log In",
                buttonTextColor = Constants.hubWhite,
                buttonIcon = Icons.Filled.Login,
                buttonHeight = 50,
                buttonWidth = 150
            ) {
                // Handle login action
                val isLoginSuccessful = viewModel.login() // Assume this returns success/failure
                if (isLoginSuccessful) {
                    viewModel.setNavigateToHome(true)
                }
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
            color = Constants.hubDarkBlue, // Dairenin rengi
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
        color = Constants.hubBlack,
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
    val customColor = Color(0xFF718A39) // Ortak renk

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange, // dışarıdan gelen değişimi yansıtıyoruz
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
                tint = Constants.hubGreen
            )
        },
        singleLine = true, // Tek satırda yazılmasını sağlamak
        isError = value.isEmpty(), // Eğer boşsa hata durumu
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
fun PhoneNumberTextField(viewModel: ResetPasswordViewModel) {
    var phoneNumberText by remember { mutableStateOf("") } // Kullanıcının girdiği telefon numarası

    OutlinedTextField(
        value = phoneNumberText, // TextField'in şu anki değeri
        onValueChange = { newValue ->
            phoneNumberText = newValue // Kullanıcı yeni bir şey yazdığında güncellenir
            viewModel.onPhoneNumberChanged(newValue) // ViewModel'e telefon numarası aktarılabilir
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp) // Yüksekliği ayarlayarak boyutu küçültüyoruz
            .padding(8.dp),
        shape = RoundedCornerShape(20.dp), // Daha yuvarlak köşeler
        label = { Text("Username", color = Constants.hubBlack) },
        leadingIcon = {
            Icon(
                imageVector = Icons.Filled.Person,
                contentDescription = "Username Icon",
                tint = Constants.hubGreen
            )
        },
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

/*@Preview
@Composable
fun MyPri(){
    val navController = rememberNavController()
    
    LoginPage(
        navController,
        viewModel = LoginViewModel()
    )
}*/