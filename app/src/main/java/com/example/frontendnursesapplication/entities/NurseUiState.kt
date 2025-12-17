package com.example.frontendnursesapplication.entities

data class NurseUiState(
    val nurses: List<Nurse> = emptyList(),
    val error: String? = null
)
