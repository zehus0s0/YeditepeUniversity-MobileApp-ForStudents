package com.example.myapplication.Views.CourseView

import androidx.lifecycle.ViewModel
import com.example.myapplication.DataLayer.Models.CourseModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class CoursesViewModel : ViewModel() {
    private val _courses = MutableStateFlow<List<CourseModel>>(emptyList())
    val courses: StateFlow<List<CourseModel>> = _courses.asStateFlow()

    private val _selectedTab = MutableStateFlow(CourseTab.MY_COURSES)
    val selectedTab: StateFlow<CourseTab> = _selectedTab.asStateFlow()

    init {
        loadCourses()
    }

    private fun loadCourses() {
        val coursesList = listOf(
            CourseModel("VCD 471", "Interactive Design Studio", "Merve Çaşkurlu"),
            CourseModel("VCD 592", "Internship", "Murat Yilmaz"),
            CourseModel("VCD 123", "Introduction to Design", "Ayşe Yavuz"),
            CourseModel("VCD 345", "Sound Studio", "Ali Gür"),
            CourseModel("VCD 432", "Digital Video", "Mahmut Şahin")
        )
        _courses.value = coursesList
    }

    fun onTabSelected(tab: CourseTab) {
        _selectedTab.value = tab
    }
}

enum class CourseTab {
    MY_COURSES,
    ALL_COURSES
}

