package com.example.a4funfacts

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

// @Serializable allows Ktor to convert the API's JSON into this class
// @Entity has Room create a database table from this class
@Entity(tableName = "facts")
@Serializable
data class FunFact(

    // PrimaryKey autogenerates because it comes from the API
    @PrimaryKey(autoGenerate = true)
    @Transient
    val id: Int = 0,

    // The fun fact where the name must match the JSON key "text" from the API
    val text: String
)