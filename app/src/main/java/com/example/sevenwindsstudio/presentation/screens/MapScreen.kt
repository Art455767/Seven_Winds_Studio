package com.example.sevenwindsstudio.presentation.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.sevenwindsstudio.R
import com.example.sevenwindsstudio.presentation.CoffeeCup
import com.example.sevenwindsstudio.presentation.navigation.Screen
import com.example.sevenwindsstudio.presentation.viewmodels.LocationsViewModel
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.mapview.MapView
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntOffset
import kotlin.math.roundToInt

@Composable
fun MapScreen(
    navController: NavController,
    selectedLocationId: Int? = null,
    viewModel: LocationsViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    val mapView = remember { MapView(context) }

    fun cleanLocationName(name: String): String {
        return name.replace("[0-9]".toRegex(), "")
            .replace("\\s+".toRegex(), " ")
            .trim()
    }

    @Composable
    fun calculateOffset(latitude: Double, longitude: Double): Offset {
        val point = Point(latitude, longitude)
        val screenPoint = mapView.mapWindow.worldToScreen(point)
        return if (screenPoint != null) {
            Offset(screenPoint.x - mapView.width / 2f, screenPoint.y - mapView.height / 2f)
        } else {
            Offset.Zero
        }
    }

    DisposableEffect(Unit) {
        mapView.onStart()
        MapKitFactory.getInstance().onStart()
        onDispose {
            mapView.onStop()
            MapKitFactory.getInstance().onStop()
        }
    }

    LaunchedEffect(state.locations, selectedLocationId) {
        mapView.map.mapObjects.clear()
        var selectedLocationPoint: Point? = null

        state.locations.forEach { locationItem ->
            val location = locationItem.location
            val point = Point(location.latitude, location.longitude)

            if (location.id == selectedLocationId) {
                selectedLocationPoint = point
            }
        }

        mapView.map.move(
            CameraPosition(
                selectedLocationPoint ?: Point(55.751574, 37.573856),
                if (selectedLocationPoint != null) 15f else 11f,
                0f,
                0f
            )
        )
    }

    Box(modifier = Modifier.fillMaxSize()) {
        AndroidView(
            factory = { mapView },
            modifier = Modifier.fillMaxSize()
        )

        Box(modifier = Modifier.matchParentSize()) {
            state.locations.forEach { locationItem ->
                val location = locationItem.location
                val isSelected = location.id == selectedLocationId
                val offset = calculateOffset(location.latitude, location.longitude)
                val cleanName = cleanLocationName(location.name) // Очищаем название

                // Показываем только если название не пустое после очистки
                if (cleanName.isNotBlank()) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .offset {
                                IntOffset(offset.x.roundToInt(), offset.y.roundToInt())
                            },
                        contentAlignment = Alignment.TopCenter
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            CoffeeCup(
                                modifier = Modifier
                                    .size(20.dp)
                                    .clickable {
                                        navController.navigate(Screen.Menu.createRoute(location.id))
                                    },
                                cupColor = if (isSelected) Color(0xFFFF5722) else Color(0xFF342D1A),
                                borderColor = if (isSelected) Color.White else Color(0xFFF6E5D1),
                                borderWidth = if (isSelected) 3.dp else 2.dp
                            )

                            Text(
                                text = cleanName, // Используем очищенное название
                                color = Color.Black,
                                modifier = Modifier.padding(top = 4.dp),
                                style = MaterialTheme.typography.caption
                            )
                        }
                    }
                }
            }
        }
    }
}