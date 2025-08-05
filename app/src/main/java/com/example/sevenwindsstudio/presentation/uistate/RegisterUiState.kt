package com.example.sevenwindsstudio.presentation.uistate

data class RegisterUiState(
    val login: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val errorMessage: String? = null
)