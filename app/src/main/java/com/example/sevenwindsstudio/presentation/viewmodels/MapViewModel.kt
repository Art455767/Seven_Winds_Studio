package com.example.sevenwindsstudio.presentation.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sevenwindsstudio.data.api.UserPreferences
import com.example.sevenwindsstudio.domain.models.Location
import com.example.sevenwindsstudio.domain.useCase.GetLocationsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val getLocationsUseCase: GetLocationsUseCase,
    private val userPreferences: UserPreferences
) : ViewModel() {
    private val _locations = mutableStateOf<List<Location>>(emptyList())
    val locations: List<Location> get() = _locations.value

    init {
        loadLocations()
    }

    private fun loadLocations() {
        viewModelScope.launch {
            userPreferences.token.first()?.let { token ->
                getLocationsUseCase(token).onSuccess { locations ->
                    _locations.value = locations
                }
            }
        }
    }
}
