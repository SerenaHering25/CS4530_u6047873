package com.example.courseviewereditor

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.Composable

// shows all fields of the selected course.
@Composable
fun CourseDetailScreen(
    course: Course,
    viewModel: CourseViewModel,
    onBackClick: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(course.displayName, style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(16.dp))

        Text("Department: ${course.department}")
        Spacer(Modifier.height(8.dp))
        Text("Course Number: ${course.courseNumber}")
        Spacer(Modifier.height(8.dp))
        Text("Location: ${course.location}")

        Spacer(Modifier.height(24.dp))

        Row {
            // Navigates back without changing any data.
            Button(onClick = onBackClick) {
                Text("Back")
            }
            Spacer(Modifier.width(8.dp))

            // Deletes the course, then navigates back.
            Button(
                onClick = {
                    viewModel.removeCourse(course)
                    onBackClick()
                },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
            ) {
                Text("Delete")
            }
        }
    }
}