package com.example.myapplication.Views.ReviewScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview

import com.example.myapplication.R

@Composable
fun ReviewCoursesScreen(
    courses: List<Course> = listOf(
        Course(id = 1, code = "VCD 471", name = "Interactive Design Studio", instructor = "Merve Çaşkurlu", rating = 5.0f),
        Course(id = 2, code = "VCD 592", name = "Internship", instructor = "Murat Yılmaz", rating = 3.2f)
    ),
    onNavigateBack: () -> Unit = {}, // Mavi oka basınca geri dönecek fonksiyon
    onCourseClick: (Course) -> Unit = {} // Yeşil oka basınca course-specific aksiyon
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF3F3F3))
    ) {
        // En Üstteki Mavi Geri Dönüş Butonu
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onNavigateBack) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_left), // Mavi ok ikonu
                    contentDescription = "Back",
                    tint = Color(0xFF3C9BFF),
                    modifier = Modifier.size(24.dp)
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Reviews",
                style = TextStyle(
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 25.sp,
                    color = Color(0xFF5F5464),
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Derslerin Listesi
        LazyColumn(modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)) {
            items(courses) { course ->
                CourseItem(course = course, onCourseClick = onCourseClick)
            }
        }
    }
}

@Composable
fun CourseItem(
    course: Course,
    onCourseClick: (Course) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .graphicsLayer {
                shadowElevation = 4.dp.value // `toPx` yerine `dp.value` ile gölge seviyesi tanımlandı
                shape = RoundedCornerShape(10.dp)
                clip = true
            },
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF9F9F9))
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = course.code,
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            color = Color(0xFF379634)
                        )
                    )
                    Text(
                        text = course.name,
                        style = TextStyle(
                            fontWeight = FontWeight.Medium,
                            fontSize = 16.sp,
                            color = Color(0xFF342E37)
                        )
                    )
                    Text(
                        text = course.instructor,
                        style = TextStyle(
                            fontWeight = FontWeight.Normal,
                            fontSize = 14.sp,
                            color = Color(0xFF5F5464)
                        )
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        repeat(5) { index ->
                            val starIcon = if (index < course.rating.toInt()) {
                                R.drawable.ic_star_filled
                            } else {
                                R.drawable.ic_star_outline
                            }

                            Icon(
                                painter = painterResource(id = starIcon),
                                contentDescription = null,
                                tint = Color(0xFF5BC658),
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }
            }

            // Sağdaki Yeşil Ok
            IconButton(
                onClick = { onCourseClick(course) },
                modifier = Modifier
                    .size(40.dp)
                    .align(Alignment.BottomEnd)
                    .padding(8.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_right), // Yeşil ok ikonu
                    contentDescription = "Arrow",
                    tint = Color(0xFF379634),
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewReviewCoursesScreen() {
    ReviewCoursesScreen()
}
