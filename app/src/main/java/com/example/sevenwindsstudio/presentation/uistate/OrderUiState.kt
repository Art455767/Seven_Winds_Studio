package com.example.sevenwindsstudio.presentation.uistate

data class OrderUiState(
    val items: List<MenuItemWithCount> = emptyList(),
    val totalPrice: Int = 0,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)