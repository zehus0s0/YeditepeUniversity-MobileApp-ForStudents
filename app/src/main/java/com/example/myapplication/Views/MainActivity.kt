package com.example.myapplication.Views

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.background
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.myapplication.Models.CourseModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            setContent {
                MaterialTheme {
                    Surface {
                        MyApp()
                    }
                }
            }
        } catch (e: Exception) {
            Log.e("MainActivity", "App Crash", e)
            throw e
        }
    }
}

@Composable
fun MyApp() {
    val courses = listOf(
        CourseModel("VCD 471", "Interactive Design Studio", "Merve Çaşkurlu"),
        CourseModel("VCD 472", "Advanced Design Studio", "Ahmet Yılmaz"),
        CourseModel("VCD 473", "UI/UX Fundamentals", "Zeynep Kaya"),
        CourseModel("VCD 474", "Animation Techniques", "Burak Demir"),
        CourseModel("VCD 475", "Game Design", "Selin Aksoy")
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
            .padding(16.dp)
    ) {
        // Başlık ve Kullanıcı Bilgisi
        Text(
            text = "Hey, Alice!",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        Text(
            text = "20212345678",
            style = MaterialTheme.typography.bodyMedium,
            color = Color(0xFF88B04B),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Arama Çubuğu
        SearchBar()
        Spacer(modifier = Modifier.height(16.dp))

        // Kategoriler
        CategorySection()
        Spacer(modifier = Modifier.height(24.dp))

        // Kaydırılabilir Kurs Listesi
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            MyCoursesSection(courses = courses)
        }

        NavigationBarSection()
    }
}

@Composable
fun MyCoursesSection(courses: List<CourseModel>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp) // Kartlar arası boşluk
    ) {
        items(courses) { course ->
            CourseCard(
                courseCode = course.courseCode,
                courseTitle = course.courseTitle,
                instructorName = course.instructorName
            )
        }
    }
}

@Composable
fun NavigationBarSection() {
    NavigationBar {
        NavigationBarItem(
            icon = { Icon(Icons.Default.Home, contentDescription = null) },
            label = { Text("Home") },
            selected = true,
            onClick = {}
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Star, contentDescription = null) },
            label = { Text("Reviews") },
            selected = false,
            onClick = {}
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Person, contentDescription = null) },
            label = { Text("Account") },
            selected = false,
            onClick = {}
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Message, contentDescription = null) },
            label = { Text("Chat") },
            selected = false,
            onClick = {}
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar() {
    var searchText by remember { mutableStateOf("") }
    TextField(
        value = searchText,
        onValueChange = { searchText = it },
        placeholder = { Text("What are you looking for?") },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null
            )
        },
        modifier = Modifier.fillMaxWidth(),
        colors = TextFieldDefaults.textFieldColors(
            containerColor = Color.White,
            focusedIndicatorColor = Color(0xFF1E3A5F),
            unfocusedIndicatorColor = Color.Transparent
        )
    )
}

@Composable
fun CategoryCard(title: String, icon: ImageVector, backgroundColor: Color) {
    Card(
        modifier = Modifier
            .size(width = 125.dp, height = 185.dp)
            .padding(4.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(65.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = title,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun CourseCard(courseCode: String, courseTitle: String, instructorName: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF4285F4))
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = courseCode,
                style = MaterialTheme.typography.headlineLarge,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = courseTitle,
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White
            )
            Text(
                text = instructorName,
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFF88B04B)
            )
        }
    }
}

@Composable
fun CategorySection() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        CategoryCard("Courses", Icons.Default.Book, Color(0xFF1E3A5F))
        CategoryCard("Groups", Icons.Default.Group, Color(0xFF4285F4))
        CategoryCard("Clubs", Icons.Default.People, Color(0xFF4285F4))
    }
}