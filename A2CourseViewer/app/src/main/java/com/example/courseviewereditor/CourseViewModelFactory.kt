package com.example.courseviewereditor

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

// The factory is the only place a CourseViewModel gets built, and it always
// goes through getInstance(), so every ViewModel shares the one repository.
class CourseViewModelFactory(private val context: Context) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    // Android calls this when it needs a ViewModel
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        // Only builds the VM it knows about
        if (modelClass.isAssignableFrom(CourseViewModel::class.java)) {
            // gets the singleton repo and pass it to new VM then cast to requested type
            return CourseViewModel(CourseRepository.getInstance(context)) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}