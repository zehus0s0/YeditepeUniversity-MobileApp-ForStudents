package com.example.myapplication.Views.CourseView

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class CourseDetailUiState(
    val courseCode: String = "VCD 471",
    val courseTitle: String = "Interactive Design Studio",
    val courseDescription: String = "In this course, students learn the theoretical and practical structure of interactive design and experience it through projects. Students discover and apply creative uses of interaction with coding and current applications.",
    val instructorName: String = "Merve Çaşkurlu",
    val instructorImageUrl: String = "", // Add actual instructor image URL
    val rating: Int = 5
)

class CourseDetailViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(CourseDetailUiState())
    val uiState: StateFlow<CourseDetailUiState> = _uiState.asStateFlow()

    fun updateCourseDetails(
        courseCode: String,
        courseTitle: String,
        courseDescription: String,
        instructorName: String,
        instructorImageUrl: String,
        rating: Int
    ) {
        _uiState.value = CourseDetailUiState(
            courseCode = courseCode,
            courseTitle = courseTitle,
            courseDescription = courseDescription,
            instructorName = instructorName,
            instructorImageUrl = instructorImageUrl,
            rating = rating
        )
    }
}