package com.example.courseviewereditor

import androidx.room.Entity
import androidx.room.PrimaryKey

// @Entity makes this class a table named "courses" in the Room database.
@Entity(tableName = "courses")
data class Course(
    // @PrimaryKey marks this column as the unique identifier for each row
    // in order for Room to differentiate the courses. autoGenerate lets the Room assign the ids itself.
    @PrimaryKey(autoGenerate = true) val id: Int = 0,

    // Columns in the table
    val department: String,
    val courseNumber: String,
    val location: String
) {
    // Declare outside of Room so it is not treated as a column.
    // Recalculated each time.
    val displayName: String
        get() = "$department $courseNumber"
}