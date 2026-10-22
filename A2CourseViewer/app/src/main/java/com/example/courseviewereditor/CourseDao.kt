package com.example.courseviewereditor

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

// The DAO defines how to read and write the courses table.
@Dao
interface CourseDao {
    // Reads all courses sorted by dept then number. Returns a Flow so
    // the list is updated when the table changes.
    @Query("SELECT * FROM courses ORDER BY department, courseNumber")
    fun getAllCourses(): Flow<List<Course>>

    // Add a course row, suspend runs in a coroutine off main thread.
    @Insert
    suspend fun insert(course: Course)

    // Remove the row with the primary key
    @Delete
    suspend fun delete(course: Course)
}