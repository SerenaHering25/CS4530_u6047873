package com.example.courseviewereditor

data class Course(
    val id: Int,
    val department: String,
    val courseNumber: String,
    val location: String
){
    val displayName: String
        get() = "$department $courseNumber"
}