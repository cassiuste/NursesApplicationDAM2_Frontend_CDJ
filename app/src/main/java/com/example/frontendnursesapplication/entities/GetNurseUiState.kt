package com.example.frontendnursesapplication.entities

import retrofit2.Response

sealed interface GetNurseUiState {

    object Idle : GetNurseUiState
    object Loading : GetNurseUiState
    object Error : GetNurseUiState
    object NotFound : GetNurseUiState

    data class Success(val nurse: Nurse) : GetNurseUiState
}
