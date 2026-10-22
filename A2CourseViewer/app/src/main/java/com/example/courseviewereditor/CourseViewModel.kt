package com.example.courseviewereditor

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

// Now takes the repository as a constructor parameter instead of owning the data.
// talks only to repo never to Room.
class CourseViewModel(private val repository: CourseRepository) : ViewModel() {

    // only this class can change the state
    private val _uiState = MutableStateFlow(CourseUiState())

    // observed so screens know what to display
    val uiState: StateFlow<CourseUiState> = _uiState.asStateFlow()

    init {
        // Keeps uiState.courses in sync with the database. Any insert or delete
        // in Room updates automatically.
        viewModelScope.launch {
            repository.courses.collect { list ->
                _uiState.update { it.copy(courses = list) }
            }
        }
    }

    // Update a part of the form field that user types in
    fun onDepartmentChange(value: String) {
        _uiState.update { it.copy(department = value) }
    }

    fun onCourseNumberChange(value: String) {
        _uiState.update { it.copy(courseNumber = value) }
    }

    fun onLocationChange(value: String) {
        _uiState.update { it.copy(location = value) }
    }

    // Called when the user taps "Add"
    fun saveCourse() {
        val state = _uiState.value
        if (state.department.isBlank() || state.courseNumber.isBlank() || state.location.isBlank()) {
            return
        }

        // Database calls are suspend functions, so they run in a coroutine so UI doesn't freeze.
        viewModelScope.launch {
            repository.addCourse(
                Course(
                    department = state.department,
                    courseNumber = state.courseNumber,
                    location = state.location
                )
            )
        }

        // Clear the form fields.
        _uiState.update { it.copy(department = "", courseNumber = "", location = "") }
    }

    fun removeCourse(course: Course) {
        viewModelScope.launch { repository.deleteCourse(course) }
    }

    fun getCourseById(id: Int): Course? {
        return _uiState.value.courses.find { it.id == id }
    }
}