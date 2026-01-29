package com.example.frontendnursesapplication.entities

data class RegisterUiState(
val loading: Boolean = false,
val success: Boolean = false,
val error: String? = null
)
