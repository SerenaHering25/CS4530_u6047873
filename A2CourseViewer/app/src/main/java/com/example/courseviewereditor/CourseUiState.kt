package com.example.courseviewereditor

data class CourseUiState(
    val courses: List<Course> = emptyList(),
    val department: String = "",
    val courseNumber: String = "",
    val location: String = ""
)