package com.example.myapplication.Views.ReviewScreen
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ReviewScreen(
    modifier: Modifier = Modifier,
    reviews: List<Review>, // Öğrencilerin yorumları
    onTeacherReviewClick: () -> Unit, // Öğretmen değerlendirme ekranı
    onCourseReviewClick: () -> Unit // Ders değerlendirme ekranı
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp) // Genel padding eklendi
    ) {
        // 'Reviews' Başlık
        Text(
            text = "Reviews",
            style = MaterialTheme.typography.titleLarge, // h5 yerine titleLarge kullanıldı.
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFF3F3F3))
                .padding(28.dp),
            color = Color(0xFF342E37),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp)) // Başlık ile butonlar arasında boşluk

        // Butonlar yan yana yerleştiriliyor
        Row(
            modifier = Modifier
                .fillMaxWidth() // Tam genişlik
                .padding(horizontal = 14.dp), // Butonlar arasında yatay boşluk
            horizontalArrangement = Arrangement.SpaceBetween // Butonlar arasına boşluk bırakılır
        ) {
            // Teachers Butonu
            Button(
                onClick = { onTeacherReviewClick() },
                modifier = Modifier
                    .width(196.dp)  // Figma'dan alınan genişlik
                    .height(46.dp),  // Figma'dan alınan yükseklik
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF3F3F3)) // Button rengi
            ) {
                Text(
                    text = "Teachers",
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = Color.Black,
                    textAlign = TextAlign.Center
                )
            }

            // Courses Butonu
            Button(
                onClick = { onCourseReviewClick() },
                modifier = Modifier
                    .width(196.dp)  // Figma'dan alınan genişlik
                    .height(46.dp),  // Figma'dan alınan yükseklik
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF3F3F3)) // Button rengi
            ) {
                Text(
                    text = "Courses",
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = Color.Black,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

