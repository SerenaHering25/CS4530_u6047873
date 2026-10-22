package com.example.courseviewereditor

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// Entities lists the tables
@Database(entities = [Course::class], version = 1)
abstract class CourseDatabase : RoomDatabase() {

    // gives the app access to the DAO
    abstract fun courseDao(): CourseDao

    // Holds the shared instance @Volatile keeps all threads up to date.
    companion object {
        @Volatile
        private var INSTANCE: CourseDatabase? = null

        // Only one database object is ever created for the whole app.
        fun getDatabase(context: Context): CourseDatabase {
            // If already exists return it otherwise lock so only one thread builds
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    CourseDatabase::class.java,
                    "course_database" // database file on device
                ).build().also { INSTANCE = it } // saves for next time
            }
        }
    }
}