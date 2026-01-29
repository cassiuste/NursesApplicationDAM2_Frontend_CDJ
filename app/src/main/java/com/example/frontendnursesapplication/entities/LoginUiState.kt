package com.example.frontendnursesapplication.entities

data class LoginUiState(
    val user: String = "",
    val password: String = "",
    val errorMessage: Boolean = false,
    val success: Boolean = false
)
