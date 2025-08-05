package com.example.sevenwindsstudio.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sevenwindsstudio.data.api.UserPreferences
import com.example.sevenwindsstudio.domain.useCase.GetMenuUseCase
import com.example.sevenwindsstudio.presentation.uistate.MenuItemWithCount
import com.example.sevenwindsstudio.presentation.uistate.MenuUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor(
    private val getMenuUseCase: GetMenuUseCase,
    val userPreferences: UserPreferences
) : ViewModel() {

    private val _uiState = MutableStateFlow(MenuUiState(isLoading = true))
    val uiState: StateFlow<MenuUiState> = _uiState

    private val selectedCounts = mutableMapOf<Int, Int>()

    fun loadMenu(token: String, locationId: Int) {
        viewModelScope.launch {
            _uiState.value = MenuUiState(isLoading = true)
            val result = getMenuUseCase(token, locationId)
            if (result.isSuccess) {
                val items = result.getOrDefault(emptyList())
                    .map { MenuItemWithCount(it, selectedCounts[it.id] ?: 0) }
                _uiState.value = MenuUiState(items = items)
            } else {
                _uiState.value = MenuUiState(errorMessage = result.exceptionOrNull()?.message)
            }
        }
    }
    fun setError(message: String) {
        _uiState.value = MenuUiState(errorMessage = message)
    }

    fun incrementCount(menuItemId: Int) {
        val currentCount = selectedCounts[menuItemId] ?: 0
        selectedCounts[menuItemId] = currentCount + 1
        updateUiState()
    }

    fun decrementCount(menuItemId: Int) {
        val currentCount = selectedCounts[menuItemId] ?: 0
        if (currentCount > 0) {
            selectedCounts[menuItemId] = currentCount - 1
            updateUiState()
        }
    }

    private fun updateUiState() {
        val items = _uiState.value.items.map {
            it.copy(count = selectedCounts[it.menuItem.id] ?: 0)
        }
        _uiState.value = _uiState.value.copy(items = items)
    }

    fun getSelectedItems() : List<MenuItemWithCount> {
        return _uiState.value.items.filter { it.count > 0 }
    }
}