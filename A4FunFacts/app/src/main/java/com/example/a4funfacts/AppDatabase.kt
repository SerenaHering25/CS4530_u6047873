package com.example.a4funfacts

import androidx.room.Database
import androidx.room.RoomDatabase

// @Database tells Room which tables entities belong in this database.
@Database(entities = [FunFact::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    // Gives access to DAO
    // Room generates the function and returns a working FactDao.
    abstract fun factDao(): FactDao
}