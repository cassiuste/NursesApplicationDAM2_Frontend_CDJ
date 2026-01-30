package com.example.frontendnursesapplication.entities

sealed interface ListAllUiState {
    object Idle : ListAllUiState
    object Loading : ListAllUiState
    object Error : ListAllUiState
    object NotFound : ListAllUiState
    data class Success(val nurses: List<Nurse>) : ListAllUiState
}