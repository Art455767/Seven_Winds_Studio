package com.example.sevenwindsstudio.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sevenwindsstudio.data.api.UserPreferences
import com.example.sevenwindsstudio.presentation.uistate.MenuItemWithCount
import com.example.sevenwindsstudio.presentation.uistate.OrderUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

//@HiltViewModel
//class OrderViewModel @Inject constructor() : ViewModel() {
//
//    private val _uiState = MutableStateFlow(OrderUiState())
//    val uiState: StateFlow<OrderUiState> = _uiState
//
//    fun setOrderItems(items: List<MenuItemWithCount>) {
//        val total = items.sumOf { it.menuItem.price * it.count }
//        _uiState.value = OrderUiState(
//            items = items,
//            totalPrice = total
//        )
//    }
//
//    fun payOrder() {
//        viewModelScope.launch {
//            _uiState.value = _uiState.value.copy(isLoading = true)
//            // Здесь должна быть логика оплаты
//            _uiState.value = _uiState.value.copy(
//                isLoading = false,
//                items = emptyList(),
//                totalPrice = 0
//            )
//        }
//    }
//}
@HiltViewModel
class OrderViewModel @Inject constructor(
    private val userPreferences: UserPreferences
) : ViewModel() {

    private val _uiState = MutableStateFlow(OrderUiState())
    val uiState: StateFlow<OrderUiState> = _uiState

    private val _paymentResult = MutableStateFlow<PaymentResult?>(null)
    val paymentResult: StateFlow<PaymentResult?> = _paymentResult

    fun setOrderItems(items: List<MenuItemWithCount>) {
        val total = items.sumOf { it.menuItem.price * it.count }
        _uiState.value = OrderUiState(
            items = items,
            totalPrice = total
        )
    }

    fun payOrder() {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(isLoading = true)

                // Получаем токен пользователя
                val token = userPreferences.token.first() ?: run {
                    _paymentResult.value = PaymentResult.Error("Требуется авторизация")
                    return@launch
                }

                // Имитация процесса оплаты
                kotlinx.coroutines.delay(2000) // Имитация сетевого запроса

                // Здесь должна быть реальная логика оплаты через API:
                // val result = paymentApi.processPayment(token, _uiState.value.totalPrice)

                // В данном примере просто имитируем успешную оплату
                _paymentResult.value = PaymentResult.Success
            } catch (e: Exception) {
                _paymentResult.value = PaymentResult.Error(e.message ?: "Ошибка оплаты")
            } finally {
                _uiState.value = _uiState.value.copy(isLoading = false)
            }
        }
    }

    fun clearOrder() {
        _uiState.value = OrderUiState()
        _paymentResult.value = null
    }
}

sealed class PaymentResult {
    data object Success : PaymentResult()
    data class Error(val message: String) : PaymentResult()
}