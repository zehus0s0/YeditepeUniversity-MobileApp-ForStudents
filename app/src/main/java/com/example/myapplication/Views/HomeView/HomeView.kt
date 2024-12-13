package com.example.myapplication.Views
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.DataLayer.Models.CourseModel
import com.example.myapplication.MyApp
import com.example.myapplication.Views.LoginView.AppNavigation
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppNavigation() // Navigation fonksiyonunu burada çağırıyoruz
        }
    }
}

@Composable
fun HomeView(navController: NavHostController) {
    val courses = listOf(
        CourseModel("VCD 471", "Interactive Design Studio", "Merve Çaşkurlu")
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF3F3F3))
            .padding(20.dp) // Genel padding
    ) {
        TitleCircle()
        // Başlık ve Kullanıcı Bilgisi
        Text(
            text = "Hey, Alice!",
            style = MaterialTheme.typography.headlineMedium,
            fontSize = 35.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        Text(
            text = "20212345678",
            fontSize = 16.sp,
            color = Color(0xFF88B04B),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Arama Çubuğu
        SearchBar()
        Spacer(modifier = Modifier.height(16.dp))

        // Kategoriler
        CategorySection()
        Spacer(modifier = Modifier.height(24.dp))

        MyCoursesWithNavButton()

        // Kaydırılabilir Kurs Listesi
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            MyCoursesSection(courses = courses)
        }
    }
}

@Composable
fun TitleCircle() {
    val circleRadius = 800.dp // Dairenin yarıçapı

    Canvas(modifier = Modifier.fillMaxWidth().height(80.dp)) {
        // Dairenin merkezini değiştirelim
        val circleCenterX = size.width / 2 // Yatayda ekranın ortasında
        val circleCenterY = -circleRadius.toPx() + 150f // Dikeyde daha aşağıya kaydırılmış, ekranın dışına daha yakın

        // Dairenin çizilmesi
        drawCircle(
            color = Color(0xFF1E3A5F), // Dairenin rengi
            radius = circleRadius.toPx(), // Yarıçap
            center = Offset(circleCenterX, circleCenterY) // Dairenin merkezi
        )
    }
}


@Composable
fun MyCoursesSection(courses: List<CourseModel>) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            //verticalArrangement = Arrangement.spacedBy(16.dp)
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
}

@Composable
fun MyCoursesWithNavButton() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(2.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // "My Courses" Text
        Text(
            text = "My Courses",
            style = MaterialTheme.typography.bodyMedium,
            fontSize = 26.sp,
            color = Color(0xFF342E37),
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Navigation Button (Right Arrow)
        IconButton(
            onClick = {
                // Navigate to the target screen
                //navController.navigate("targetScreen")
            }
        ) {
            Icon(
                imageVector = Icons.Filled.ArrowForward,
                contentDescription = "Navigate",
                tint = Color(0xFF9EC7F2)
            )
        }
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
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .shadow(10.dp, shape = RoundedCornerShape(16.dp)) // Shadow added
            .clip(RoundedCornerShape(16.dp)), // Corner radius applied
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
            .size(width = 118.dp, height = 190.dp)
            .shadow(5.dp, shape = RoundedCornerShape(16.dp)) // Gölgeleri düzgün göstermek için şekil ekledik
            .padding(4.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize() // Column içeriği tam olarak kapsar
                .padding(5.dp), // İçeriğin kenarlara daha yakın olmasını sağlarız
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
                fontSize = 16.sp,
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
            .shadow(10.dp)
            //.width(350.dp)
            .height(240.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF3F3F3)),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Baby blue box with course code
            Box(
                modifier = Modifier

                    .align(Alignment.TopCenter)
                    .background(color = Color(0xFF9EC7F2), shape = RoundedCornerShape(16.dp))
                    .padding(horizontal = 12.dp, vertical = 8.dp)
                    .width(371.dp)
                    .height(130.dp),

                ) {
                Text(
                    modifier = Modifier
                        .align(Alignment.Center),
                    text = courseCode,
                    fontSize = 64.sp,
                    color = Color(0xFFFFFFFF),
                    fontWeight = FontWeight.Bold
                )
            }

            // Course title and instructor name
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(top = 16.dp)
            ) {
                Text(
                    text = courseTitle,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF342E37)
                )
                Text(
                    text = instructorName,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF718A39)
                )
            }
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
