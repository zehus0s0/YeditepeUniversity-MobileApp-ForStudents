package com.example.myapplication.Views.ReviewScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.example.myapplication.R
import android.util.Log





val OpenSansSemiBold = FontFamily(
    Font(R.font.open_sans_semibold)
)

@Composable
fun ReviewScreen(
    modifier: Modifier = Modifier,
    teachers: List<Teacher> = listOf(
        Teacher(id = 1, name = "Merve Çaşkurlu", rating = 5.0f, photo = R.drawable.teacher_1),
        Teacher(id = 2, name = "Murat Yılmaz", rating = 2.8f, photo = R.drawable.teacher_2)
    ),
    onTeacherReviewClick: () -> Unit = {},
    onCourseReviewClick: () -> Unit = {},
    onTeacherClick: (Teacher) -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF3F3F3))
    ) {
        Text(
            text = "Reviews",
            style = TextStyle(
                fontFamily = OpenSansSemiBold,
                fontWeight = FontWeight.SemiBold,
                fontSize = 25.sp,
                color = Color(0xFF5F5464),
                textAlign = TextAlign.Center
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 50.dp)
                .height(34.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            CustomShadowButton(
                text = "Teachers",
                onClick = onTeacherReviewClick
            )
            Spacer(modifier = Modifier.width(16.dp))
            CustomShadowButton(
                text = "Courses",
                onClick = onCourseReviewClick
            )
        }

        LazyColumn(modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp)) {
            items(teachers) { teacher ->
                TeacherItem(teacher = teacher, onTeacherClick = onTeacherClick)
            }
        }
    }
}


@Composable
fun CustomShadowButton(
    text: String,
    onClick: () -> Unit
) {
    Button(
        onClick = {
            Log.d("ButtonClicked", "$text button clicked")
            onClick()
        },
        modifier = Modifier
            .width(180.dp)
            .height(43.dp)
            .graphicsLayer {
                shadowElevation = 4.dp.toPx()
                shape = RoundedCornerShape(10.dp)
                clip = true
            }
            .background(Color(0xFFF3F3F3)),
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF3F3F3))
    ) {
        Text(
            text = text,
            style = TextStyle(
                fontFamily = OpenSansSemiBold,
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp,
                letterSpacing = 0.sp
            ),
            color = Color(0xFF5F5464),
        )
    }
}

@Composable
fun TeacherItem(teacher: Teacher, onTeacherClick: (Teacher) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .graphicsLayer {
                shadowElevation = 4.dp.toPx()
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
                Image(
                    painter = painterResource(id = teacher.photo),
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .size(110.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.width(16.dp))

                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = teacher.name,
                        style = TextStyle(
                            fontFamily = OpenSansSemiBold,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 20.sp,
                            color = Color(0xFF342E37)
                        )
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .width(112.63.dp) // Figma genişliği
                            .height(19.76.dp) // Figma yüksekliği
                    ) {
                        repeat(5) { index ->
                            val starIcon = when {
                                index < teacher.rating.toInt() -> R.drawable.ic_star_filled
                                index < teacher.rating.toInt() + 1 && teacher.rating % 1 != 0f -> R.drawable.ic_star_half
                                else -> R.drawable.ic_star_outline
                            }

                            Icon(
                                painter = painterResource(id = starIcon),
                                contentDescription = null,
                                tint = Color(0xFF5BC658),
                                modifier = Modifier
                                    .size(19.76.dp) // Yıldız boyutunu ayarla
                            )
                        }
                    }
                }
            }

            IconButton(
                onClick = { onTeacherClick(teacher) },
                modifier = Modifier
                    .size(40.dp)
                    .align(Alignment.BottomEnd)
                    .padding(8.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_right),
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
fun PreviewReviewScreen() {
    ReviewScreen()
}
