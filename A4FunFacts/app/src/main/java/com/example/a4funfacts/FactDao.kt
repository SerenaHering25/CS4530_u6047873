package com.example.a4funfacts

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FactDao {

    // Save one fact and Room generates the id
    @Insert
    suspend fun insert(fact: FunFact)

    // All saved facts, newest first.
    // Flow allows updates automatically when a fact is added.
    @Query("SELECT * FROM facts ORDER BY id DESC")
    fun getAll(): Flow<List<FunFact>>
}