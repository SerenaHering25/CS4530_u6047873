package com.example.a4funfacts

import android.app.Application
import androidx.room.Room

// Only one application exists so everything is a singleton
class FactsApp : Application() {

    // Room database created when first used
    val database: AppDatabase by lazy {
        Room.databaseBuilder(
            this,
            AppDatabase::class.java,
            "facts_db"
        ).build()
    }

    // Ktor client that is shared
    val client by lazy { ApiClient.client }

    // Repository using the database and client
    val repository: FactRepo by lazy {
        FactRepo(database.factDao(), client)
    }
}