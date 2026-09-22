package com.example.courseviewereditor

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class CourseViewModel : ViewModel() {

    private var nextId = 1 // counter for unique id for each course

    // keeps private so nothing outside this class can change the UI state
    private val _uiState = MutableStateFlow(CourseUiState())

    // Composables get the current state and are notified of changes automatically
    val uiState: StateFlow<CourseUiState> = _uiState.asStateFlow()

    // Updates on field of the "add course" form
    fun onDepartmentChange(value: String) {
        _uiState.update { it.copy(department = value) }
    }

    fun onCourseNumberChange(value: String) {
        _uiState.update { it.copy(courseNumber = value) }
    }

    fun onLocationChange(value: String) {
        _uiState.update { it.copy(location = value) }
    }

    // save new course
    fun saveCourse() {
        val state = _uiState.value
        // if any field blank do nothing and exit early
        if (state.department.isBlank() || state.courseNumber.isBlank() || state.location.isBlank()) {
            return
        }

        _uiState.update { current ->
            current.copy(
                // add a new course to existing list
                courses = current.courses + Course(
                    id = nextId++,
                    department = current.department,
                    courseNumber = current.courseNumber,
                    location = current.location
                ),
                // clear the form after course has been saved
                department = "",
                courseNumber = "",
                location = ""
            )
        }
    }

    // delete existing course
    fun removeCourse(course: Course) {
        // rebuild course list with deleted course subtracted
        _uiState.update { it.copy(courses = it.courses - course) }
    }

    // Used by the detail screen to look up the clicked course by id
    fun getCourseById(id: Int): Course? {
        return _uiState.value.courses.find { it.id == id }
    }
}