package com.example.myapplication.Views.ReviewScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ReviewScreen(
    modifier: Modifier = Modifier,
    reviews: List<Review> = emptyList(), // Varsayılan boş bir liste
    teachers: List<Teacher> = listOf( // Varsayılan bir öğretmen listesi
        Teacher(id = 1, name = "Merve Çaşkurlu", rating = 4.5f),
        Teacher(id = 2, name = "Murat Yılmaz", rating = 3.8f),
        Teacher(id = 3, name = "Ayşe Yavuz", rating = 4.2f),
        Teacher(id = 4, name = "Ali Gür", rating = 3.9f),
        Teacher(id = 5, name = "Mahmut Şahin", rating = 4.1f)
    ),
    onTeacherReviewClick: () -> Unit = {}, // Varsayılan olarak boş fonksiyon
    onCourseReviewClick: () -> Unit = {}  // Varsayılan olarak boş fonksiyon
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {
        // 'Reviews' Başlık
        Text(
            text = "Reviews",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFF3F3F3))
                .padding(28.dp),
            color = Color(0xFF342E37),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Butonlar yan yana yerleştiriliyor
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Teachers Butonu
            Button(
                onClick = { onTeacherReviewClick() },
                modifier = Modifier
                    .width(196.dp)
                    .height(46.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF3F3F3))
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
                    .width(196.dp)
                    .height(46.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF3F3F3))
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

        Spacer(modifier = Modifier.height(16.dp))

        // Öğretmenler Listesi
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(teachers) { teacher ->
                TeacherItem(teacher = teacher)
            }
        }
    }
}

@Composable
fun TeacherItem(teacher: Teacher) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(Color(0xFFF9F9F9))
            .padding(16.dp)
    ) {
        Text(
            text = teacher.name,
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            ),
            color = Color(0xFF342E37)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Rating: ${teacher.rating}",
            style = MaterialTheme.typography.bodyMedium,
            color = Color(0xFF6A6A6A)
        )
    }
}

// @Preview Anotasyonu ile ekranın önizlemesini yapılabilir.
@Preview(showBackground = true)
@Composable
fun PreviewReviewScreen() {
    ReviewScreen()
}
