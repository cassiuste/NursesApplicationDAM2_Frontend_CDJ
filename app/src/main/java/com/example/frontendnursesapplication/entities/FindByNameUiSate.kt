package com.example.frontendnursesapplication.entities

sealed interface FindNameUiState {
    object Idle : FindNameUiState
    object Loading : FindNameUiState
    object Error : FindNameUiState
    object NotFound : FindNameUiState
    data class Success(val nurse: Nurse) : FindNameUiState
}
