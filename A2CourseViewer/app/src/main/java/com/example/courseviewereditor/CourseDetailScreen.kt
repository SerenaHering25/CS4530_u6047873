package com.example.courseviewereditor

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color

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
            // back button navigation
            Button(onClick = onBackClick) {
                Text("Back")
            }
            // delete button
            Spacer(Modifier.width(8.dp))
            Button(
                onClick = {
                    viewModel.removeCourse(course)
                    onBackClick()
                },
                // red for delete button
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
            ) {
                Text("Delete")
            }
        }
    }
}