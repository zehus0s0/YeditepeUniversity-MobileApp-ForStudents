package com.example.myapplication.Views.CourseView

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.sp
import com.example.myapplication.Utilities.Constants

@Composable
fun CourseDetailScreen(
    viewModel: CourseDetailViewModel,
    onBackClick: () -> Unit,
    onChatClick: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        // TitleCircle ekleniyor
        Canvas(modifier = Modifier
            .fillMaxWidth()
            .height(380.dp)
        ) {
            val circleRadius = 800.dp.toPx()
            val circleCenterX = size.width / 2
            val circleCenterY = -circleRadius + 210f

            this.drawCircle(
                color = Constants.hubBabyBlue,
                radius = circleRadius,
                center = Offset(circleCenterX, circleCenterY)
            )
        }

        // İçerik
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Geri butonu
            IconButton(
                onClick = onBackClick,
                modifier = Modifier.align(Alignment.Start)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Geri",
                    tint = Color.White
                )
            }

            Spacer(modifier = Modifier.height(60.dp))

            // Ders kodu
            Surface(
                color = Constants.hubGreen,
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .width(157.dp)
                    .height(58.dp)
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(
                        text = uiState.courseCode,
                        color = Constants.hubWhite,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontSize = 28.sp
                        ),
                        modifier = Modifier
                            .width(129.dp)
                            .height(35.dp),
                        textAlign = TextAlign.Center
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Yıldızlar
            Row(
                horizontalArrangement = Arrangement.Center
            ) {
                repeat(5) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = Constants.hubGreen,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Ders başlığı
            Text(
                text = uiState.courseTitle,
                style = MaterialTheme.typography.headlineMedium,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Ders açıklaması
            Text(
                text = uiState.courseDescription,
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                color = Constants.hubDark
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Eğitmen bölümü
            Text(
                text = "Instructor:",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = 8.dp)
            ) {
                AsyncImage(
                    model = uiState.instructorImageUrl,
                    contentDescription = "Instructor image",
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
                Text(
                    text = uiState.instructorName,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(start = 16.dp),
                    color = Constants.hubGreen
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Chat butonu
            Button(
                onClick = onChatClick,
                modifier = Modifier
                    .width(96.dp)
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Constants.hubBabyBlue),
                shape = RoundedCornerShape(15.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Chat",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Constants.hubWhite
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(
                        imageVector = Icons.Default.ArrowForward,
                        contentDescription = null,
                        tint = Constants.hubWhite,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CourseDetailScreenPreview() {
    val previewViewModel = CourseDetailViewModel().apply {
        updateCourseDetails(
            courseCode = "VCD 471",
            courseTitle = "Etkileşim Tasarımı Stüdyosu",
            courseDescription = "Bu derste öğrenciler, etkileşimli tasarımın teorik ve pratik yapısını öğrenir ve projeler aracılığıyla deneyimler. Öğrenciler kodlama ve güncel uygulamalarla etkileşimin yaratıcı kullanımlarını keşfeder ve uygular.",
            instructorName = "Merve Çaşkurlu",
            instructorImageUrl = "https://picsum.photos/200", // Örnek bir resim URL'si
            rating = 5
        )
    }
    
    CourseDetailScreen(
        viewModel = previewViewModel,
        onBackClick = {},
        onChatClick = {}
    )
}
//laylay