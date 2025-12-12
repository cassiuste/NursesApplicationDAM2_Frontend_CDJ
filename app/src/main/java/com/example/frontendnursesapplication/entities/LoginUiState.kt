package com.example.frontendnursesapplication.entities

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val errorMessage: Boolean = false,
    val success: Boolean = false
)
