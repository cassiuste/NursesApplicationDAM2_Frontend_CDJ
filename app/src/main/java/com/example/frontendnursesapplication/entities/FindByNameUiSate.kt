package com.example.frontendnursesapplication.entities

sealed interface FindByNameUiSate {
    object Idle : FindByNameUiSate
    object Loading : FindByNameUiSate
    object Error : FindByNameUiSate
    object NotFound : FindByNameUiSate
    data class Success(val nurse: Nurse) : FindByNameUiSate
}
