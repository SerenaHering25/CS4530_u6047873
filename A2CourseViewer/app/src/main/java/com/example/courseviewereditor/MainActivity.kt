package com.example.courseviewereditor

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    CourseApp()
                }
            }
        }
    }
}

@androidx.compose.runtime.Composable
fun CourseApp() {
    // tracks which screen is currently shown
    val navController = rememberNavController()
    // created once and passed down to both screens
    val viewModel: CourseViewModel = viewModel()

    // defines the screens for the app
    NavHost(navController = navController, startDestination = "courseList") {
        // route for main screen
        composable("courseList") {
            CourseListScreen(
                viewModel = viewModel,
                onCourseClick = { course ->
                    navController.navigate("courseDetail/${course.id}")
                }
            )
        }
        // route for detail screen
        composable(
            route = "courseDetail/{courseId}",
            arguments = listOf(navArgument("courseId") { type = NavType.IntType })
        ) { backStackEntry ->
            // Gets courseId back out of the route arguments, if null return null
            val courseId = backStackEntry.arguments?.getInt("courseId") ?: return@composable
            // look up full course object from ViewModel using the id
            val course = viewModel.getCourseById(courseId)

            if (course != null) {
                CourseDetailScreen(
                    course = course,
                    viewModel = viewModel,
                    onBackClick = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}