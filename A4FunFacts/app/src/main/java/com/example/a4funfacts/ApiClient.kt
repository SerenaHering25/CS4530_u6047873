package com.example.a4funfacts

import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

// Singleton allows only one HttpClient for the whole app
object ApiClient {
    val client = HttpClient(Android) {
        install(ContentNegotiation) {
            // Skip extra JSON fields the API sends that FunFact doesn't have
            json(Json { ignoreUnknownKeys = true })
        }
    }
}