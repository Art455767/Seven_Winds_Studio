package com.example.sevenwindsstudio.presentation.viewmodels

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.os.CancellationSignal
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.sevenwindsstudio.data.api.UserPreferences
import com.example.sevenwindsstudio.domain.useCase.GetLocationsUseCase
import com.example.sevenwindsstudio.presentation.uistate.LocationItem
import com.example.sevenwindsstudio.presentation.uistate.LocationsUiState
import com.example.sevenwindsstudio.utils.LocationPermissionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.asExecutor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withTimeoutOrNull
import javax.inject.Inject
import kotlin.coroutines.resume

@HiltViewModel
class LocationsViewModel @Inject constructor(
    private val getLocationsUseCase: GetLocationsUseCase,
    private val application: Application,
    val userPreferences: UserPreferences
) : AndroidViewModel(application) {

    private val _uiState = MutableStateFlow(LocationsUiState(isLoading = true))
    val uiState: StateFlow<LocationsUiState> = _uiState

    init {
        userPreferences.token
            .onEach(::loadLocations)
            .launchIn(viewModelScope)
    }

    fun loadLocations(token: String? = null) {
        viewModelScope.launch {
            _uiState.value = LocationsUiState(isLoading = true)

            val token = token ?: userPreferences.token.first()
            if (token == null) {
                _uiState.value = LocationsUiState(needAuth = true)
                return@launch
            }

            try {
                val result = getLocationsUseCase(token)
                if (result.isSuccess) {
                    // Просто передаем список кофеен без расстояний
                    _uiState.value = LocationsUiState(
                        locations = result.getOrDefault(emptyList()).map {
                            LocationItem(location = it)
                        }
                    )
                } else {
                    _uiState.value = LocationsUiState(
                        error = result.exceptionOrNull()?.message ?: "Ошибка загрузки"
                    )
                }
            } catch (e: Exception) {
                _uiState.value = LocationsUiState(error = e.message ?: "Ошибка")
            }
        }
    }
}

    @SuppressLint("MissingPermission")
    private suspend fun getCurrentLocation(locationManager: LocationManager): Location? =
        suspendCancellableCoroutine { continuation ->
            val provider = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                LocationManager.FUSED_PROVIDER
            } else {
                LocationManager.GPS_PROVIDER
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                val cancellationSignal = CancellationSignal()
                continuation.invokeOnCancellation { cancellationSignal.cancel() }
                locationManager.getCurrentLocation(
                    provider,
                    cancellationSignal,
                    Dispatchers.IO.asExecutor()
                ) { continuation.resume(it) }
            } else {
                val listener = LocationListener { continuation.resume(it) }
                continuation.invokeOnCancellation {
                    locationManager.removeUpdates(listener)
                }
                locationManager.requestSingleUpdate(provider, listener, null)
            }
        }
