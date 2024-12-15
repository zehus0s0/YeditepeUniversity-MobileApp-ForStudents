import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import com.example.myapplication.R
import com.example.myapplication.Utilities.Constants

@Composable
fun SplashScreen(onSplashCompleted: () -> Unit) {

    val img = R.drawable.app_logo
    var scale by remember { mutableStateOf(0.dp) }

    // Logo animasyon boyutunu güncellediğimiz kısım
    val animatedScale by animateDpAsState(
        targetValue = scale,
        animationSpec = tween(durationMillis = 1000)
    )

    LaunchedEffect(Unit) {
        scale = 200.dp // Logonun ulaşacağı son boyut
        delay(1500)    // Animasyon ve bekleme süresi
        onSplashCompleted() // Splash tamamlandıktan sonra yönlendirme
    }

    Box(
        modifier = Modifier
            .background(Constants.hubDarkBlue)
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(R.drawable.app_logo),
            contentDescription = "Uygulama Logosu",
            modifier = Modifier.size(animatedScale) // Animasyonlu boyut
        )
    }
}


