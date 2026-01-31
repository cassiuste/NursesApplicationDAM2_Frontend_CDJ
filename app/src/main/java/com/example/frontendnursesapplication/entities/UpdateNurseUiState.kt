package com.example.frontendnursesapplication.entities

sealed class UpdateNurseUiState {
    object Idle : UpdateNurseUiState()
    object Loading : UpdateNurseUiState()
    object Success : UpdateNurseUiState()
    object Error : UpdateNurseUiState()
}
