// OrderScreen.kt
package com.example.sevenwindsstudio.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Error
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.sevenwindsstudio.presentation.uistate.MenuItemWithCount
import com.example.sevenwindsstudio.presentation.uistate.OrderUiState
import com.example.sevenwindsstudio.presentation.viewmodels.OrderViewModel
import com.example.sevenwindsstudio.presentation.viewmodels.PaymentResult

@Composable
fun OrderScreen(
    navController: NavController,
    viewModel: OrderViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()
    val paymentResult by viewModel.paymentResult.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Ваш заказ") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Назад")
                    }
                }
            )
        },
        bottomBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text("Время ожидания заказа")
                Text("15 минут!", style = MaterialTheme.typography.h6.copy(fontWeight = FontWeight.Bold))
                Text("Спасибо, что выбрали нас!")

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { viewModel.payOrder() },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary)
                ) {
                    Text("Оплатить", fontSize = 18.sp)
                }
            }
        }
    ) { padding ->
        if (state.items.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text("Ваш заказ пуст")
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                items(state.items) { item ->
                    OrderItemRow(item)
                    Divider()
                }
            }
        }
    }
}

@Composable
private fun OrderContent(
    state: OrderUiState,
    viewModel: OrderViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(state.items) { item ->
                OrderItemRow(item)
                Divider()
            }
        }

        OrderInfoSection(totalPrice = state.totalPrice)

        Button(
            onClick = { viewModel.payOrder() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            enabled = !state.isLoading
        ) {
            Text("Оплатить", fontSize = 18.sp)
        }
    }
}

@Composable
private fun OrderItemRow(item: MenuItemWithCount) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(item.menuItem.name ?: "", style = MaterialTheme.typography.subtitle1)
            Text("${item.count} × ${item.menuItem.price} руб.",
                style = MaterialTheme.typography.body2)
        }
        Text("${item.menuItem.price * item.count} руб.",
            style = MaterialTheme.typography.subtitle1)
    }
}

@Composable
private fun OrderInfoSection(totalPrice: Int) {
    Column(
        modifier = Modifier.padding(vertical = 16.dp)
    ) {
        Divider()
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Время ожидания заказа \"15 минут! Спасибо, что выбрали нас!\"",
            style = MaterialTheme.typography.body1
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Итого:", style = MaterialTheme.typography.h6)
            Text("$totalPrice руб.", style = MaterialTheme.typography.h6)
        }
    }
}

@Composable
private fun PaymentSuccessScreen(onContinue: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Default.CheckCircle,
            contentDescription = "Success",
            tint = MaterialTheme.colors.primary,
            modifier = Modifier.size(64.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text("Оплата прошла успешно!", style = MaterialTheme.typography.h5)
        Spacer(modifier = Modifier.height(32.dp))
        Button(onClick = onContinue) {
            Text("Вернуться в меню")
        }
    }
}

@Composable
private fun PaymentErrorScreen(
    error: String,
    onRetry: () -> Unit,
    onCancel: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Default.Error,
            contentDescription = "Error",
            tint = MaterialTheme.colors.error,
            modifier = Modifier.size(64.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text("Ошибка оплаты", style = MaterialTheme.typography.h5)
        Spacer(modifier = Modifier.height(8.dp))
        Text(error, color = MaterialTheme.colors.error)
        Spacer(modifier = Modifier.height(32.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = onCancel) {
                Text("Отмена")
            }
            Button(onClick = onRetry) {
                Text("Повторить")
            }
        }
    }
}