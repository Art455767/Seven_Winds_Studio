package com.example.sevenwindsstudio.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.LocalCafe
import androidx.compose.material.icons.filled.Payment
import androidx.compose.material.icons.filled.Remove
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.sevenwindsstudio.presentation.navigation.Screen
import com.example.sevenwindsstudio.presentation.uistate.MenuItemWithCount
import com.example.sevenwindsstudio.presentation.viewmodels.MenuViewModel

@Composable
fun MenuScreen(
    navController: NavController,
    locationId: Int,
    viewModel: MenuViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()
    val token by viewModel.userPreferences.token.collectAsState(initial = null)

    LaunchedEffect(locationId, token) {
        try {
            token?.let { viewModel.loadMenu(it, locationId) }
        } catch (e: Exception) {
            viewModel.setError(e.message ?: "Ошибка загрузки")
        }
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Меню кофейни") }) },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = {
                    val selectedItems = viewModel.getSelectedItems()
                    if (selectedItems.isNotEmpty()) {
                        navController.currentBackStackEntry?.savedStateHandle?.set(
                            "orderItems",
                            selectedItems
                        )
                        navController.navigate(Screen.Order.route)
                    }
                },
                icon = { Icon(Icons.Default.Payment, contentDescription = "Оплатить") },
                text = { Text("Перейти к оплате") },
                backgroundColor = MaterialTheme.colors.primary
            )
        }
    ) { padding ->
        when {
            state.isLoading -> LoadingScreen(padding)
            state.errorMessage != null -> ErrorScreen(state.errorMessage!!, padding)
            else -> MenuContent(state.items, padding, viewModel)
        }
    }
}

@Composable
private fun MenuContent(
    items: List<MenuItemWithCount>,
    padding: PaddingValues,
    viewModel: MenuViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(8.dp)
        ) {
            items(items) { item ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                        .padding(8.dp)
                ) {
                    MenuItemCard(
                        item = item,
                        onAdd = { viewModel.incrementCount(item.menuItem.id) },
                        onRemove = { viewModel.decrementCount(item.menuItem.id) }
                    )
                }
            }
        }
    }
}

@Composable
fun MenuItemCard(
    item: MenuItemWithCount,
    onAdd: () -> Unit,
    onRemove: () -> Unit
) {
    Card(
        elevation = 4.dp,
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(MaterialTheme.colors.secondary.copy(alpha = 0.1f)),
                contentAlignment = Alignment.Center
            ) {
                if (item.menuItem.imageUrl != null) {
                    AsyncImage(
                        model = item.menuItem.imageUrl,
                        contentDescription = item.menuItem.name,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.LocalCafe,
                        contentDescription = item.menuItem.name,
                        tint = MaterialTheme.colors.primary,
                        modifier = Modifier.size(48.dp)
                    )
                }
            }

            Text(
                text = item.menuItem.name ?: "Без названия",
                style = MaterialTheme.typography.subtitle1,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "${item.menuItem.price} руб.",
                    style = MaterialTheme.typography.subtitle2,
                    color = MaterialTheme.colors.primary
                )

                Counter(
                    count = item.count,
                    onAdd = onAdd,
                    onRemove = onRemove
                )
            }
        }
    }
}

@Composable
private fun Counter(
    count: Int,
    onAdd: () -> Unit,
    onRemove: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End
    ) {
        IconButton(
            onClick = onRemove,
            modifier = Modifier.size(36.dp)
        ) {
            Icon(Icons.Default.Remove, "Уменьшить", tint = MaterialTheme.colors.primary)
        }

        Text(
            text = count.toString(),
            style = MaterialTheme.typography.h6,
            modifier = Modifier.padding(horizontal = 4.dp)
        )

        IconButton(
            onClick = onAdd,
            modifier = Modifier.size(36.dp)
        ) {
            Icon(Icons.Default.Add, "Увеличить", tint = MaterialTheme.colors.primary)
        }
    }
}

@Composable
private fun TotalPriceCard(totalPrice: Int) {
    Card(
        elevation = 4.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Итого:", style = MaterialTheme.typography.h6)
            Text(
                "$totalPrice руб.",
                style = MaterialTheme.typography.h6,
                color = MaterialTheme.colors.primary
            )
        }
    }
}

@Composable
private fun LoadingScreen(padding: PaddingValues) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun ErrorScreen(message: String, padding: PaddingValues) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding),
        contentAlignment = Alignment.Center
    ) {
        Text(message, color = MaterialTheme.colors.error)
    }
}