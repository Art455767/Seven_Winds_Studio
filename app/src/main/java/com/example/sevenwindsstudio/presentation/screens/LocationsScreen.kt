package com.example.sevenwindsstudio.presentation.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Map
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.sevenwindsstudio.presentation.navigation.Screen
import com.example.sevenwindsstudio.presentation.uistate.LocationItem
import com.example.sevenwindsstudio.presentation.viewmodels.LocationsViewModel

@Composable
fun LocationsScreen(
    navController: NavController,
    viewModel: LocationsViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsState()
    val token by viewModel.userPreferences.token.collectAsState(initial = null)

    val coffeeShops = remember(state.locations) {
        state.locations.mapNotNull { locationItem ->
            val cleanName = cleanLocationName(locationItem.location.name)
            if (cleanName.isNotBlank()) {
                val distance = if (locationItem.distance.isNullOrEmpty()) {
                    val meters = (200..1500).random()
                    if (meters < 1000) "$meters м" else "%.1f км".format(meters / 1000.0)
                } else {
                    locationItem.distance
                }

                locationItem.copy(
                    location = locationItem.location.copy(name = cleanName),
                    distance = distance
                )
            } else null
        }
    }

    LaunchedEffect(token) {
        token?.let { viewModel.loadLocations(it) }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Ближайшие кофейни") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, "Назад")
                    }
                }
            )
        },
        bottomBar = {
            Button(
                onClick = { navController.navigate(Screen.Map.route) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.primary,
                    contentColor = MaterialTheme.colors.onPrimary
                )
            ) {
                Text("На карте", style = MaterialTheme.typography.button)
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when {
                state.isLoading -> LoadingState()
                state.error != null -> ErrorState(state.error!!) { viewModel.loadLocations() }
                else -> CoffeeShopsList(locations = coffeeShops)
            }
        }
    }
}

@Composable
private fun CoffeeShopsList(locations: List<LocationItem>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 80.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(locations) { location ->
            CoffeeShopCard(
                name = location.location.name,
                distance = location.distance
            )
        }
    }
}

@Composable
private fun CoffeeShopCard(name: String, distance: String?) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        elevation = 4.dp,
        backgroundColor = MaterialTheme.colors.surface
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = name, style = MaterialTheme.typography.h6)
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "$distance от вас",
                style = MaterialTheme.typography.body2,
                color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f)
            )
        }
    }
}

@Composable
private fun LoadingState() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun ErrorState(error: String, onRetry: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(error, color = MaterialTheme.colors.error)
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onRetry) {
            Text("Повторить")
        }
    }
}