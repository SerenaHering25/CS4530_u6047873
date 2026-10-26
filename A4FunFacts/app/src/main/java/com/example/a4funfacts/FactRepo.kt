package com.example.a4funfacts

import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.flow.Flow

// Repository is the only class that talks to Room and Ktor
class FactRepo(
    private val factDao: FactDao,
    private val client: HttpClient
) {

    // Live list of all saved facts from Room
    fun getFacts(): Flow<List<FunFact>> = factDao.getAll()

    // Fetch a random fact from the API, then save it to Room
    suspend fun fetchAndSaveFact() {
        try {
            val fact: FunFact = client
                .get("https://uselessfacts.jsph.pl/api/v2/facts/random")
                .body()
            factDao.insert(fact)   // Flow picks this up automatically
        } catch (e: Exception) {
            Log.e("FunFact", "Error Fetching", e)
        }
    }
}