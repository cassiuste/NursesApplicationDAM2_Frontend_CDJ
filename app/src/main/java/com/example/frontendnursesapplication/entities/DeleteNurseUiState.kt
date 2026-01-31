package com.example.frontendnursesapplication.entities

sealed interface DeleteNurseUiState{

    object Idle : DeleteNurseUiState
    object Loading : DeleteNurseUiState
    object Success : DeleteNurseUiState
    object Error: DeleteNurseUiState

}