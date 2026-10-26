package com.example.a4funfacts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

// ViewModel only talks to the Repository
class FactVM(private val repo: FactRepo) : ViewModel() {

    // Turn the repository's Flow into a StateFlow the UI can observe.
    val facts: StateFlow<List<FunFact>> = repo.getFacts()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Called by the button and runs in the background
    fun fetchFact() {
        viewModelScope.launch { repo.fetchAndSaveFact() }
    }
}

// Factory tells Android how to build FactVM with a repository inside
class FactVMFactory(private val repo: FactRepo) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return FactVM(repo) as T
    }
}