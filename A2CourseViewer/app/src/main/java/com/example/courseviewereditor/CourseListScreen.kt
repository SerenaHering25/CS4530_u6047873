package com.example.courseviewereditor

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun CourseListScreen(
    viewModel: CourseViewModel,
    onCourseClick: (Course) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Add a Course", style = MaterialTheme.typography.titleMedium)
        Spacer(Modifier.height(8.dp))

        OutlinedTextField(
            value = uiState.department,
            onValueChange = viewModel::onDepartmentChange,
            label = { Text("Department") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = uiState.courseNumber,
            onValueChange = viewModel::onCourseNumberChange,
            label = { Text("Course Number") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = uiState.location,
            onValueChange = viewModel::onLocationChange,
            label = { Text("Location") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(8.dp))
        // add button
        Button(
            onClick = viewModel::saveCourse,
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Add")
        }

        Text("Courses", style = MaterialTheme.typography.titleMedium)

        // scrollable list
        LazyColumn {
            items(uiState.courses) { course ->
                ListItem(
                    headlineContent = { Text(course.displayName) },
                    // allows for course to be clicked and taken to detail screen
                    modifier = Modifier.clickable { onCourseClick(course) }
                )
            }
        }
    }
}