package com.example.sevenwindsstudio.presentation.uistate

import com.example.sevenwindsstudio.domain.models.Location


data class LocationItem(
    val location: Location,
    val distance: String? = null
)

data class LocationsUiState(
    val locations: List<LocationItem> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val needAuth: Boolean = false,
)