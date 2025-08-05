package com.example.sevenwindsstudio.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sevenwindsstudio.data.api.UserPreferences
import com.example.sevenwindsstudio.presentation.uistate.SplashUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(userPreferences: UserPreferences) : ViewModel() {

    private val _state = MutableStateFlow(SplashUiState.INITIAL)
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            val token = userPreferences.token.first()

            _state.update {
                if (token != null) {
                    SplashUiState.AUTHORIZED
                } else {
                    SplashUiState.UNAUTHORIZED
                }
            }
        }
    }
}
