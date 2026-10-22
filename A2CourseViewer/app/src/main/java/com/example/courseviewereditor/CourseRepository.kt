package com.example.courseviewereditor

import android.content.Context
import kotlinx.coroutines.flow.Flow

// ViewModel only talks to this class not the DAO or database.
// This keeps one repo for the whole app.
class CourseRepository private constructor(private val courseDao: CourseDao) {

    // allows for new list to be created when the table changes
    val courses: Flow<List<Course>> = courseDao.getAllCourses()

    // passes insert through DAO
    suspend fun addCourse(course: Course) = courseDao.insert(course)

    // passes delete through DAO
    suspend fun deleteCourse(course: Course) = courseDao.delete(course)

    // One shared repo. @Volatile keeps all threads up to date
    companion object {
        @Volatile
        private var INSTANCE: CourseRepository? = null

        // Only way to get repo from outside this class
        fun getInstance(context: Context): CourseRepository {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: CourseRepository(
                    CourseDatabase.getDatabase(context).courseDao()
                ).also { INSTANCE = it }
            }
        }
    }
}