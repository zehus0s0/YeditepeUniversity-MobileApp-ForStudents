package com.example.myapplication.Views.CourseView

import android.graphics.drawable.Drawable.ConstantState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.myapplication.DataLayer.Models.CourseModel
import com.example.myapplication.Utilities.Constants


// Composable Screen
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoursesScreen(
    viewModel: CoursesViewModel = viewModel(),
    onNavigateBack: () -> Unit = {},
    onCourseClick: (CourseModel) -> Unit = {}
) {
    val courses by viewModel.courses.collectAsState()
    val selectedTab by viewModel.selectedTab.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Constants.hubWhite)
    ) {
        // Top Bar
        TopAppBar(
            title = { Text("Courses", fontSize = 20.sp) },
            navigationIcon = {
                IconButton(onClick = onNavigateBack) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
            },
            colors = TopAppBarDefaults.smallTopAppBarColors(
                containerColor = Color(0xFFF3F3F3)
            )
        )

        // Tabs
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            TabButton(
                text = "My Courses",
                selected = selectedTab == CourseTab.MY_COURSES,
                onClick = { viewModel.onTabSelected(CourseTab.MY_COURSES) }
            )
            TabButton(
                text = "All Courses",
                selected = selectedTab == CourseTab.ALL_COURSES,
                onClick = { viewModel.onTabSelected(CourseTab.ALL_COURSES) }
            )
        }

        // Course List
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(courses) { course ->
                CourseCard(course = course, onClick = { onCourseClick(course) })
            }
        }
    }
}


@Composable
fun TabButton(
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .height(if (selected) 46.dp else 40.dp) // Aktif buton daha büyük
            .width(if (selected) 196.dp else 196.dp) // Aktif buton daha geniş
            .padding(horizontal = 4.dp), // Butonlar arasında mesafe
        colors = ButtonDefaults.buttonColors(
            containerColor = if (selected) Color(0xFFF3F3F3) else Color(0xFFF3F3F3) // Arka plan rengi
        ),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 4.dp, // Gölge etkisi
            pressedElevation = 6.dp
        ),
        shape = MaterialTheme.shapes.medium
    ) {
        Text(
            text = text,
            color = if (selected) Color(0xFF718A39) else Color(0xFF718A39), // Yazı rengi
            style = MaterialTheme.typography.labelLarge
        )
    }
}


@Composable
fun CourseCard(
    course: CourseModel,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .width(438.dp) // Kart genişliği
            .height(95.dp) // Kart yüksekliği
            .padding(start = 16.dp, top = 8.dp) // Sol ve üst boşluk
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        ),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = Constants.hubWhite
        )
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Course Code Badge
            Surface(
                color = Constants.hubBabyBlue,
                shape = MaterialTheme.shapes.small
            ) {
                Text(
                    text = course.courseCode,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                    color = Constants.hubWhite
                )
            }

            // Course Details
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 16.dp)
            ) {
                Text(
                    text = course.courseTitle,
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.Black,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = course.instructorName,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Constants.hubGreen,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            // Arrow Icon
            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = "View Course",
                tint = Color(0xFF76A053)
            )
        }
    }
}

@Preview
@Composable
fun myPreview(){
    CoursesScreen()
}
