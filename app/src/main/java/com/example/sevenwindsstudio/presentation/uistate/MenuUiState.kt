package com.example.sevenwindsstudio.presentation.uistate

import com.example.sevenwindsstudio.domain.models.MenuItem

data class MenuUiState(
    val items: List<MenuItemWithCount> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

data class MenuItemWithCount(
    val menuItem: MenuItem,
    val count: Int = 0
)